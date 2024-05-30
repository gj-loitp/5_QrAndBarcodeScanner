package com.mckimquyen.barcodescanner.feature.tabs.create.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.extension.isNotBlank
import com.mckimquyen.barcodescanner.extension.textString
import com.mckimquyen.barcodescanner.feature.tabs.create.FragmentBaseCreateBarcode
import com.mckimquyen.barcodescanner.model.schema.Phone
import com.mckimquyen.barcodescanner.model.schema.Schema
import kotlinx.android.synthetic.main.f_create_qr_code_phone.*

class FragmentCreateQrCodePhone : FragmentBaseCreateBarcode() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.f_create_qr_code_phone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEditText()
        handleTextChanged()
    }

    override fun showPhone(phone: String) {
        editText.apply {
            setText(phone)
            setSelection(phone.length)
        }
    }

    override fun getBarcodeSchema(): Schema {
        return Phone(editText.textString)
    }

    private fun initEditText() {
        editText.requestFocus()
    }

    private fun handleTextChanged() {
        editText.addTextChangedListener {
            parentActivity.isCreateBarcodeButtonEnabled = editText.isNotBlank()
        }
    }
}
