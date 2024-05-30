package com.mckimquyen.barcodescanner.feature.tabs.create.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.extension.isNotBlank
import com.mckimquyen.barcodescanner.extension.textString
import com.mckimquyen.barcodescanner.feature.tabs.create.FragmentBaseCreateBarcode
import com.mckimquyen.barcodescanner.model.schema.Schema
import com.mckimquyen.barcodescanner.model.schema.Wifi
import kotlinx.android.synthetic.main.f_create_qr_code_wifi.*

class CreateQrCodeWifiFragmentBaseCreateBarcode : FragmentBaseCreateBarcode() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.f_create_qr_code_wifi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEncryptionTypesSpinner()
        initNetworkNameEditText()
        handleTextChanged()
    }

    override fun getBarcodeSchema(): Schema {
        val encryption = when (spinnerEncryption.selectedItemPosition) {
            0 -> "WPA"
            1 -> "WEP"
            2 -> "nopass"
            else -> "nopass"
        }
        return Wifi(
            encryption = encryption,
            name = editTextNetworkName.textString,
            password = editTextPassword.textString,
            isHidden = checkBoxIsHidden.isChecked
        )
    }

    private fun initEncryptionTypesSpinner() {
        spinnerEncryption.adapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.fragment_create_qr_code_wifi_encryption_types, R.layout.i_spinner
        ).apply {
            setDropDownViewResource(R.layout.i_spinner_dropdown)
        }

        spinnerEncryption.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                textInputLayoutPassword.isVisible = position != 2
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun initNetworkNameEditText() {
        editTextNetworkName.requestFocus()
    }

    private fun handleTextChanged() {
        editTextNetworkName.addTextChangedListener { toggleCreateBarcodeButton() }
        editTextPassword.addTextChangedListener { toggleCreateBarcodeButton() }
    }

    private fun toggleCreateBarcodeButton() {
        parentActivity.isCreateBarcodeButtonEnabled = editTextNetworkName.isNotBlank()
    }
}