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
import com.mckimquyen.barcodescanner.model.schema.Mms
import com.mckimquyen.barcodescanner.model.schema.Schema
import kotlinx.android.synthetic.main.fragment_create_qr_code_mms.*

class CreateQrCodeMmsFragment : BaseCreateBarcodeFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_qr_code_mms, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTitleEditText()
        handleTextChanged()
    }

    override fun showPhone(phone: String) {
        editTextPhone.apply {
            setText(phone)
            setSelection(phone.length)
        }
    }

    override fun getBarcodeSchema(): Schema {
        return Mms(
            phone = editTextPhone.textString,
            subject = edit_text_subject.textString,
            message = editTextMessage.textString
        )
    }

    private fun initTitleEditText() {
        editTextPhone.requestFocus()
    }

    private fun handleTextChanged() {
        editTextPhone.addTextChangedListener { toggleCreateBarcodeButton() }
        edit_text_subject.addTextChangedListener { toggleCreateBarcodeButton() }
        editTextMessage.addTextChangedListener { toggleCreateBarcodeButton() }
    }

    private fun toggleCreateBarcodeButton() {
        parentActivity.isCreateBarcodeButtonEnabled = editTextPhone.isNotBlank() || edit_text_subject.isNotBlank() || editTextMessage.isNotBlank()
    }
}