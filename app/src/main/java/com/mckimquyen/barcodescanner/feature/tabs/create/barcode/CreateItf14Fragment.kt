package com.mckimquyen.barcodescanner.feature.tabs.create.barcode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.extension.textString
import com.mckimquyen.barcodescanner.feature.tabs.create.BaseCreateBarcodeFragment
import com.mckimquyen.barcodescanner.model.schema.Other
import com.mckimquyen.barcodescanner.model.schema.Schema
import kotlinx.android.synthetic.main.f_create_itf_14.*

class CreateItf14Fragment : BaseCreateBarcodeFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.f_create_itf_14, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editText.requestFocus()
        editText.addTextChangedListener {
            parentActivity.isCreateBarcodeButtonEnabled = (editText.text?.length?.rem(2)) == 0
        }
    }

    override fun getBarcodeSchema(): Schema {
        return Other(editText.textString)
    }
}