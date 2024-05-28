package com.mckimquyen.barcodescanner.feature.tabs.create.barcode

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.extension.applySystemWindowInsets
import com.mckimquyen.barcodescanner.feature.BaseActivity
import com.mckimquyen.barcodescanner.feature.tabs.create.CreateBarcodeActivity
import com.google.zxing.BarcodeFormat
import kotlinx.android.synthetic.main.a_create_barcode_all.*

class CreateBarcodeAllActivity : BaseActivity() {

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, CreateBarcodeAllActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_create_barcode_all)
        supportEdgeToEdge()
        handleToolbarBackClicked()
        handleButtonsClicked()
    }

    private fun supportEdgeToEdge() {
        rootView.applySystemWindowInsets(applyTop = true, applyBottom = true)
    }

    private fun handleToolbarBackClicked() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun handleButtonsClicked() {
        buttonDataMatrix.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.DATA_MATRIX) }
        buttonAztec.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.AZTEC) }
        buttonPdf417.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.PDF_417) }
        buttonCodabar.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.CODABAR) }
        buttonCode39.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.CODE_39) }
        buttonCode93.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.CODE_93) }
        buttonCode128.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.CODE_128) }
        buttonEan8.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.EAN_8) }
        buttonEan13.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.EAN_13) }
        buttonItf14.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.ITF) }
        buttonUpcA.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.UPC_A) }
        buttonUpcE.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.UPC_E) }
    }
}