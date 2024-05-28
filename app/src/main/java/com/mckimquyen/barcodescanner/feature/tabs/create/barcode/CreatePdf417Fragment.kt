package com.mckimquyen.barcodescanner.feature.tabs.create.barcode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.extension.isNotBlank
import com.mckimquyen.barcodescanner.extension.textString
import com.mckimquyen.barcodescanner.feature.tabs.create.BaseCreateBarcodeFragment
import com.mckimquyen.barcodescanner.model.schema.Other
import com.mckimquyen.barcodescanner.model.schema.Schema
import kotlinx.android.synthetic.main.fragment_create_pdf417.*

class CreatePdf417Fragment : BaseCreateBarcodeFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_pdf417, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editText.requestFocus()
        editText.addTextChangedListener {
            parentActivity.isCreateBarcodeButtonEnabled = editText.isNotBlank()
        }
    }

    override fun getBarcodeSchema(): Schema {
        return Other(editText.textString)
    }
}