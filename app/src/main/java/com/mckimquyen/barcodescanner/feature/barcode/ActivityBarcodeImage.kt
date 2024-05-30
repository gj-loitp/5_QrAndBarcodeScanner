package com.mckimquyen.barcodescanner.feature.barcode

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.di.barcodeImageGenerator
import com.mckimquyen.barcodescanner.di.settings
import com.mckimquyen.barcodescanner.extension.applySystemWindowInsets
import com.mckimquyen.barcodescanner.extension.toStringId
import com.mckimquyen.barcodescanner.extension.unsafeLazy
import com.mckimquyen.barcodescanner.feature.ActivityBase
import com.mckimquyen.barcodescanner.model.Barcode
import com.mckimquyen.barcodescanner.usecase.Logger
import kotlinx.android.synthetic.main.activity_barcode_image.*
import java.text.SimpleDateFormat
import java.util.*

class ActivityBarcodeImage : ActivityBase() {

    companion object {
        private const val BARCODE_KEY = "BARCODE_KEY"

        fun start(context: Context, barcode: Barcode) {
            val intent = Intent(context, ActivityBarcodeImage::class.java)
            intent.putExtra(BARCODE_KEY, barcode)
            context.startActivity(intent)
        }
    }

    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH)
    private val barcode by unsafeLazy {
        intent?.getSerializableExtra(BARCODE_KEY) as? Barcode ?: throw IllegalArgumentException("No barcode passed")
    }
    private var originalBrightness: Float = 0.5f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_image)
        supportEdgeToEdge()
        saveOriginalBrightness()
        handleToolbarBackPressed()
        handleToolbarMenuItemClicked()
        showMenu()
        showBarcode()
    }

    private fun supportEdgeToEdge() {
        rootView.applySystemWindowInsets(applyTop = true, applyBottom = true)
    }

    private fun saveOriginalBrightness() {
        originalBrightness = window.attributes.screenBrightness
    }

    private fun handleToolbarBackPressed() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun handleToolbarMenuItemClicked() {
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.itemIncreaseBrightness -> {
                    increaseBrightnessToMax()
                    toolbar.menu.apply {
                        findItem(R.id.itemIncreaseBrightness).isVisible = false
                        findItem(R.id.itemDecreaseBrightness).isVisible = true
                    }
                }

                R.id.itemDecreaseBrightness -> {
                    restoreOriginalBrightness()
                    toolbar.menu.apply {
                        findItem(R.id.itemDecreaseBrightness).isVisible = false
                        findItem(R.id.itemIncreaseBrightness).isVisible = true
                    }
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun showMenu() {
        toolbar.inflateMenu(R.menu.menu_barcode_image)
    }

    private fun showBarcode() {
        showBarcodeImage()
        showBarcodeDate()
        showBarcodeFormat()
        showBarcodeText()
    }

    private fun showBarcodeImage() {
        try {
            val bitmap = barcodeImageGenerator.generateBitmap(
                barcode = barcode,
                width = 2000,
                height = 2000,
                margin = 0,
                codeColor = settings.barcodeContentColor,
                backgroundColor = settings.barcodeBackgroundColor
            )
            image_view_barcode.setImageBitmap(bitmap)
            image_view_barcode.setBackgroundColor(settings.barcodeBackgroundColor)
            layout_barcode_image_background.setBackgroundColor(settings.barcodeBackgroundColor)

            if (settings.isDarkTheme.not() || settings.areBarcodeColorsInversed) {
                layout_barcode_image_background.setPadding(
                    /* left = */ 0,
                    /* top = */ 0,
                    /* right = */ 0,
                    /* bottom = */ 0
                )
            }
        } catch (ex: Exception) {
            Logger.log(ex)
            image_view_barcode.isVisible = false
        }
    }

    private fun showBarcodeDate() {
        textViewDate.text = dateFormatter.format(barcode.date)
    }

    private fun showBarcodeFormat() {
        val format = barcode.format.toStringId()
        toolbar.setTitle(format)
    }

    private fun showBarcodeText() {
        text_view_barcode_text.text = barcode.text
    }

    private fun increaseBrightnessToMax() {
        setBrightness(1.0f)
    }

    private fun restoreOriginalBrightness() {
        setBrightness(originalBrightness)
    }

    private fun setBrightness(brightness: Float) {
        window.attributes = window.attributes.apply {
            screenBrightness = brightness
        }
    }
}
