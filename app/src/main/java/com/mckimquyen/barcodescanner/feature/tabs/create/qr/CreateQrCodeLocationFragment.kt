package com.mckimquyen.barcodescanner.feature.tabs.create.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.extension.isNotBlank
import com.mckimquyen.barcodescanner.extension.textString
import com.mckimquyen.barcodescanner.feature.tabs.create.BaseCreateBarcodeFragment
import com.mckimquyen.barcodescanner.model.schema.Geo
import com.mckimquyen.barcodescanner.model.schema.Schema
import kotlinx.android.synthetic.main.f_create_qr_code_location.*

class CreateQrCodeLocationFragment : BaseCreateBarcodeFragment() {

    override val latitude: Double?
        get() = editTextLatitude.textString.toDoubleOrNull()

    override val longitude: Double?
        get() = editTextLongitude.textString.toDoubleOrNull()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.f_create_qr_code_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLatitudeEditText()
        handleTextChanged()
    }

    override fun getBarcodeSchema(): Schema {
       return Geo(
           latitude = editTextLatitude.textString,
           longitude = editTextLongitude.textString,
           altitude = editTextAltitude.textString
       )
    }

    override fun showLocation(latitude: Double?, longitude: Double?) {
        latitude?.apply {
            editTextLatitude.setText(latitude.toString())
        }
        longitude?.apply {
            editTextLongitude.setText(longitude.toString())
        }
    }

    private fun initLatitudeEditText() {
        editTextLatitude.requestFocus()
    }

    private fun handleTextChanged() {
        editTextLatitude.addTextChangedListener { toggleCreateBarcodeButton() }
        editTextLongitude.addTextChangedListener { toggleCreateBarcodeButton() }
    }

    private fun toggleCreateBarcodeButton() {
        parentActivity.isCreateBarcodeButtonEnabled = editTextLatitude.isNotBlank() && editTextLongitude.isNotBlank()
    }
}