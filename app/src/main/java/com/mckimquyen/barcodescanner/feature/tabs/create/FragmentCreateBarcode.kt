package com.mckimquyen.barcodescanner.feature.tabs.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.extension.applySystemWindowInsets
import com.mckimquyen.barcodescanner.extension.clipboardManager
import com.mckimquyen.barcodescanner.extension.orZero
import com.mckimquyen.barcodescanner.feature.tabs.create.barcode.CreateBarcodeAllActivityBase
import com.mckimquyen.barcodescanner.feature.tabs.create.qr.CreateQrCodeAllActivityBase
import com.mckimquyen.barcodescanner.model.schema.BarcodeSchema
import com.google.zxing.BarcodeFormat
import kotlinx.android.synthetic.main.f_create_barcode.*

class FragmentCreateBarcode : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.f_create_barcode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supportEdgeToEdge()
        handleButtonsClicked()
    }

    private fun supportEdgeToEdge() {
        appBarLayout.applySystemWindowInsets(applyTop = true)
    }

    private fun handleButtonsClicked() {
        // QR code
        buttonClipboard.setOnClickListener {
            ActivityCreateBarcode.start(
                requireActivity(),
                BarcodeFormat.QR_CODE,
                BarcodeSchema.OTHER,
                getClipboardContent()
            )
        }
        buttonText.setOnClickListener {
            ActivityCreateBarcode.start(
                requireActivity(),
                BarcodeFormat.QR_CODE,
                BarcodeSchema.OTHER
            )
        }
        buttonUrl.setOnClickListener {
            ActivityCreateBarcode.start(
                requireActivity(),
                BarcodeFormat.QR_CODE,
                BarcodeSchema.URL
            )
        }
        buttonWifi.setOnClickListener {
            ActivityCreateBarcode.start(
                requireActivity(),
                BarcodeFormat.QR_CODE,
                BarcodeSchema.WIFI
            )
        }
        buttonLocation.setOnClickListener {
            ActivityCreateBarcode.start(
                requireActivity(),
                BarcodeFormat.QR_CODE,
                BarcodeSchema.GEO
            )
        }
        buttonContactVcard.setOnClickListener {
            ActivityCreateBarcode.start(
                requireActivity(),
                BarcodeFormat.QR_CODE,
                BarcodeSchema.VCARD
            )
        }
        buttonShowAllQrCode.setOnClickListener {
            CreateQrCodeAllActivityBase.start(requireActivity())
        }

        // Barcode
        buttonCreateBarcode.setOnClickListener {
            CreateBarcodeAllActivityBase.start(requireActivity())
        }
    }

    private fun getClipboardContent(): String {
        val clip = requireActivity().clipboardManager?.primaryClip ?: return ""
        return when (clip.itemCount.orZero()) {
            0 -> ""
            else -> clip.getItemAt(0).text.toString()
        }
    }
}
