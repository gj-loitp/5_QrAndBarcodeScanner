package com.mckimquyen.barcodescanner.feature.tabs.create.qr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.extension.applySystemWindowInsets
import com.mckimquyen.barcodescanner.feature.ActivityBase
import com.mckimquyen.barcodescanner.feature.tabs.create.ActivityCreateBarcode
import com.mckimquyen.barcodescanner.model.schema.BarcodeSchema
import com.google.zxing.BarcodeFormat
import kotlinx.android.synthetic.main.a_create_qr_code_all.*

class ActivityCreateQrCodeAll : ActivityBase() {

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, ActivityCreateQrCodeAll::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_create_qr_code_all)
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
        buttonText.setOnClickListener {
            ActivityCreateBarcode.start(
                context = this,
                barcodeFormat = BarcodeFormat.QR_CODE,
                barcodeSchema = BarcodeSchema.OTHER
            )
        }
        buttonUrl.setOnClickListener {
            ActivityCreateBarcode.start(
                context = this,
                barcodeFormat = BarcodeFormat.QR_CODE,
                barcodeSchema = BarcodeSchema.URL
            )
        }
        buttonWifi.setOnClickListener {
            ActivityCreateBarcode.start(
                context = this,
                barcodeFormat = BarcodeFormat.QR_CODE,
                barcodeSchema = BarcodeSchema.WIFI
            )
        }
        buttonLocation.setOnClickListener {
            ActivityCreateBarcode.start(
                context = this,
                barcodeFormat = BarcodeFormat.QR_CODE,
                barcodeSchema = BarcodeSchema.GEO
            )
        }
        buttonOtp.setOnClickListener {
            ActivityCreateBarcode.start(
                context = this,
                barcodeFormat = BarcodeFormat.QR_CODE,
                barcodeSchema = BarcodeSchema.OTP_AUTH
            )
        }
        buttonContactVcard.setOnClickListener {
            ActivityCreateBarcode.start(
                context = this,
                barcodeFormat = BarcodeFormat.QR_CODE,
                barcodeSchema = BarcodeSchema.VCARD
            )
        }
        buttonContactMecard.setOnClickListener {
            ActivityCreateBarcode.start(
                context = this,
                barcodeFormat = BarcodeFormat.QR_CODE,
                barcodeSchema = BarcodeSchema.MECARD
            )
        }
        buttonEvent.setOnClickListener {
            ActivityCreateBarcode.start(
                context = this,
                barcodeFormat = BarcodeFormat.QR_CODE,
                barcodeSchema = BarcodeSchema.VEVENT
            )
        }
        buttonPhone.setOnClickListener {
            ActivityCreateBarcode.start(
                context = this,
                barcodeFormat = BarcodeFormat.QR_CODE,
                barcodeSchema = BarcodeSchema.PHONE
            )
        }
        buttonEmail.setOnClickListener {
            ActivityCreateBarcode.start(
                context = this,
                barcodeFormat = BarcodeFormat.QR_CODE,
                barcodeSchema = BarcodeSchema.EMAIL
            )
        }
        buttonSms.setOnClickListener {
            ActivityCreateBarcode.start(
                context = this,
                barcodeFormat = BarcodeFormat.QR_CODE,
                barcodeSchema = BarcodeSchema.SMS
            )
        }
        buttonMms.setOnClickListener {
            ActivityCreateBarcode.start(
                context = this,
                barcodeFormat = BarcodeFormat.QR_CODE,
                barcodeSchema = BarcodeSchema.MMS
            )
        }
        buttonCryptoCurrency.setOnClickListener {
            ActivityCreateBarcode.start(
                context = this,
                barcodeFormat = BarcodeFormat.QR_CODE,
                barcodeSchema = BarcodeSchema.CRYPTOCURRENCY
            )
        }
        buttonBookmark.setOnClickListener {
            ActivityCreateBarcode.start(
                context = this,
                barcodeFormat = BarcodeFormat.QR_CODE,
                barcodeSchema = BarcodeSchema.BOOKMARK
            )
        }
        buttonApp.setOnClickListener {
            ActivityCreateBarcode.start(
                context = this,
                barcodeFormat = BarcodeFormat.QR_CODE,
                barcodeSchema = BarcodeSchema.APP
            )
        }
    }
}
