package com.mckimquyen.barcodescanner.feature.tabs.create.qr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.extension.applySystemWindowInsets
import com.mckimquyen.barcodescanner.feature.ActivityBase
import com.mckimquyen.barcodescanner.feature.tabs.create.CreateBarcodeActivityBase
import com.mckimquyen.barcodescanner.model.schema.BarcodeSchema
import com.google.zxing.BarcodeFormat
import kotlinx.android.synthetic.main.a_create_qr_code_all.*

class CreateQrCodeAllActivityBase : ActivityBase() {

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, CreateQrCodeAllActivityBase::class.java)
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
        buttonText.setOnClickListener { CreateBarcodeActivityBase.start(this, BarcodeFormat.QR_CODE, BarcodeSchema.OTHER) }
        buttonUrl.setOnClickListener { CreateBarcodeActivityBase.start(this, BarcodeFormat.QR_CODE, BarcodeSchema.URL) }
        buttonWifi.setOnClickListener { CreateBarcodeActivityBase.start(this, BarcodeFormat.QR_CODE, BarcodeSchema.WIFI) }
        buttonLocation.setOnClickListener { CreateBarcodeActivityBase.start(this, BarcodeFormat.QR_CODE, BarcodeSchema.GEO) }
        buttonOtp.setOnClickListener { CreateBarcodeActivityBase.start(this, BarcodeFormat.QR_CODE, BarcodeSchema.OTP_AUTH) }
        buttonContactVcard.setOnClickListener { CreateBarcodeActivityBase.start(this, BarcodeFormat.QR_CODE, BarcodeSchema.VCARD) }
        buttonContactMecard.setOnClickListener { CreateBarcodeActivityBase.start(this, BarcodeFormat.QR_CODE, BarcodeSchema.MECARD) }
        buttonEvent.setOnClickListener { CreateBarcodeActivityBase.start(this, BarcodeFormat.QR_CODE, BarcodeSchema.VEVENT) }
        buttonPhone.setOnClickListener { CreateBarcodeActivityBase.start(this, BarcodeFormat.QR_CODE, BarcodeSchema.PHONE) }
        buttonEmail.setOnClickListener { CreateBarcodeActivityBase.start(this, BarcodeFormat.QR_CODE, BarcodeSchema.EMAIL) }
        buttonSms.setOnClickListener { CreateBarcodeActivityBase.start(this, BarcodeFormat.QR_CODE, BarcodeSchema.SMS) }
        buttonMms.setOnClickListener { CreateBarcodeActivityBase.start(this, BarcodeFormat.QR_CODE, BarcodeSchema.MMS) }
        buttonCryptoCurrency.setOnClickListener { CreateBarcodeActivityBase.start(this, BarcodeFormat.QR_CODE, BarcodeSchema.CRYPTOCURRENCY) }
        buttonBookmark.setOnClickListener { CreateBarcodeActivityBase.start(this, BarcodeFormat.QR_CODE, BarcodeSchema.BOOKMARK) }
        buttonApp.setOnClickListener { CreateBarcodeActivityBase.start(this, BarcodeFormat.QR_CODE, BarcodeSchema.APP) }
    }
}