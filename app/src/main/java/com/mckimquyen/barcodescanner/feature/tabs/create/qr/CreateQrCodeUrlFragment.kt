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
import com.mckimquyen.barcodescanner.model.schema.Schema
import com.mckimquyen.barcodescanner.model.schema.Url
import kotlinx.android.synthetic.main.f_create_qr_code_url.*

class CreateQrCodeUrlFragment : BaseCreateBarcodeFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.f_create_qr_code_url, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showUrlPrefix()
        handleTextChanged()
    }

    override fun getBarcodeSchema(): Schema {
        return Url(editText.textString)
    }

    private fun showUrlPrefix() {
        val prefix = "https://"
        editText.apply {
            setText(prefix)
            setSelection(prefix.length)
            requestFocus()
        }
    }

    private fun handleTextChanged() {
        editText.addTextChangedListener {
            parentActivity.isCreateBarcodeButtonEnabled = editText.isNotBlank()
        }
    }
}