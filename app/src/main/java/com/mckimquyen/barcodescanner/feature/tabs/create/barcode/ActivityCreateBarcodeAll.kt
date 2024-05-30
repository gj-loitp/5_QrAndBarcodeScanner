package com.mckimquyen.barcodescanner.feature.tabs.create.barcode

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.extension.applySystemWindowInsets
import com.mckimquyen.barcodescanner.feature.ActivityBase
import com.mckimquyen.barcodescanner.feature.tabs.create.ActivityCreateBarcode
import com.google.zxing.BarcodeFormat
import kotlinx.android.synthetic.main.a_create_barcode_all.*

class ActivityCreateBarcodeAll : ActivityBase() {

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, ActivityCreateBarcodeAll::class.java)
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
        buttonDataMatrix.setOnClickListener {
            ActivityCreateBarcode.start(context = this, barcodeFormat = BarcodeFormat.DATA_MATRIX)
        }
        buttonAztec.setOnClickListener {
            ActivityCreateBarcode.start(context = this, barcodeFormat = BarcodeFormat.AZTEC)
        }
        buttonPdf417.setOnClickListener {
            ActivityCreateBarcode.start(context = this, barcodeFormat = BarcodeFormat.PDF_417)
        }
        buttonCodabar.setOnClickListener {
            ActivityCreateBarcode.start(context = this, barcodeFormat = BarcodeFormat.CODABAR)
        }
        buttonCode39.setOnClickListener {
            ActivityCreateBarcode.start(context = this, barcodeFormat = BarcodeFormat.CODE_39)
        }
        buttonCode93.setOnClickListener {
            ActivityCreateBarcode.start(context = this, barcodeFormat = BarcodeFormat.CODE_93)
        }
        buttonCode128.setOnClickListener {
            ActivityCreateBarcode.start(context = this, barcodeFormat = BarcodeFormat.CODE_128)
        }
        buttonEan8.setOnClickListener {
            ActivityCreateBarcode.start(context = this, barcodeFormat = BarcodeFormat.EAN_8)
        }
        buttonEan13.setOnClickListener {
            ActivityCreateBarcode.start(context = this, barcodeFormat = BarcodeFormat.EAN_13)
        }
        buttonItf14.setOnClickListener {
            ActivityCreateBarcode.start(context = this, barcodeFormat = BarcodeFormat.ITF)
        }
        buttonUpcA.setOnClickListener {
            ActivityCreateBarcode.start(context = this, barcodeFormat = BarcodeFormat.UPC_A)
        }
        buttonUpcE.setOnClickListener {
            ActivityCreateBarcode.start(context = this, barcodeFormat = BarcodeFormat.UPC_E)
        }
    }
}
