package com.mckimquyen.barcodescanner.feature.barcode.save

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.di.barcodeImageGenerator
import com.mckimquyen.barcodescanner.di.barcodeImageSaver
import com.mckimquyen.barcodescanner.di.permissionsHelper
import com.mckimquyen.barcodescanner.extension.applySystemWindowInsets
import com.mckimquyen.barcodescanner.extension.showError
import com.mckimquyen.barcodescanner.extension.unsafeLazy
import com.mckimquyen.barcodescanner.feature.ActivityBase
import com.mckimquyen.barcodescanner.model.Barcode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.a_save_barcode_as_image.*

class ActivitySaveBarcodeAsImage : ActivityBase() {

    companion object {
        private const val REQUEST_PERMISSIONS_CODE = 101
        private val PERMISSIONS = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )

        private const val BARCODE_KEY = "BARCODE_KEY"

        fun start(context: Context, barcode: Barcode) {
            val intent = Intent(context, ActivitySaveBarcodeAsImage::class.java).apply {
                putExtra(BARCODE_KEY, barcode)
            }
            context.startActivity(intent)
        }
    }

    private val barcode by unsafeLazy {
        intent?.getSerializableExtra(BARCODE_KEY) as? Barcode ?: throw IllegalArgumentException("No barcode passed")
    }

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_save_barcode_as_image)
        supportEdgeToEdge()
        initToolbar()
        initFormatSpinner()
        initSaveButton()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("roy93~", "onRequestPermissionsResult")
        if (permissionsHelper.areAllPermissionsGranted(grantResults)) {
            Log.d("roy93~", "if")
            saveBarcode()
        } else {
            Log.d("roy93~", "else")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                saveBarcode()
            } else {
                //do nothing
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    private fun supportEdgeToEdge() {
        rootView.applySystemWindowInsets(applyTop = true, applyBottom = true)
    }

    private fun initToolbar() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initFormatSpinner() {
        spinnerSaveAs.adapter = ArrayAdapter.createFromResource(
            /* context = */ this,
            /* textArrayResId = */ R.array.activity_save_barcode_as_image_formats,
            /* textViewResId = */ R.layout.i_spinner
        ).apply {
            setDropDownViewResource(R.layout.i_spinner_dropdown)
        }
    }

    private fun initSaveButton() {
        buttonSave.setOnClickListener {
            Log.d("roy93~", "setOnClickListener")
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        Log.d("roy93~", "requestPermissions")
        permissionsHelper.requestPermissions(
            activity = this,
            permissions = PERMISSIONS,
            requestCode = REQUEST_PERMISSIONS_CODE
        )
    }

    private fun saveBarcode() {
        val saveFunc = when (spinnerSaveAs.selectedItemPosition) {
            0 -> {
                barcodeImageGenerator.generateBitmapAsync(
                    barcode = barcode,
                    width = 640,
                    height = 640,
                    margin = 2
                )
                    .flatMapCompletable {
                        barcodeImageSaver.savePngImageToPublicDirectory(this, it, barcode)
                    }
            }

            1 -> {
                barcodeImageGenerator.generateSvgAsync(
                    barcode = barcode,
                    width = 640,
                    height = 640,
                    margin = 2
                )
                    .flatMapCompletable {
                        barcodeImageSaver.saveSvgImageToPublicDirectory(
                            context = this,
                            image = it,
                            barcode = barcode
                        )
                    }
            }

            else -> return
        }

        showLoading(true)

        saveFunc
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    showBarcodeSaved()
                },
                { error ->
                    showLoading(false)
                    showError(error)
                }
            )
            .addTo(disposable)
    }

    private fun showLoading(isLoading: Boolean) {
        progressBarLoading.isVisible = isLoading
        scrollView.isVisible = isLoading.not()
    }

    private fun showBarcodeSaved() {
        Toast.makeText(this, R.string.activity_save_barcode_as_image_file_name_saved, Toast.LENGTH_LONG).show()
        finish()
    }
}
