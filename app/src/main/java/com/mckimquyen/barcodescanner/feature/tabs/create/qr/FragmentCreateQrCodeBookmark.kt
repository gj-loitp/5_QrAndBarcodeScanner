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
import com.mckimquyen.barcodescanner.model.schema.Bookmark
import com.mckimquyen.barcodescanner.model.schema.Schema
import kotlinx.android.synthetic.main.f_create_qr_code_bookmark.*

class FragmentCreateQrCodeBookmark : FragmentBaseCreateBarcode() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.f_create_qr_code_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTitleEditText()
        handleTextChanged()
    }

    override fun getBarcodeSchema(): Schema {
        return Bookmark(
            title = editTextTitle.textString,
            url = editTextUrl.textString
        )
    }

    private fun initTitleEditText() {
        editTextTitle.requestFocus()
    }

    private fun handleTextChanged() {
        editTextTitle.addTextChangedListener {
            toggleCreateBarcodeButton()
        }
        editTextUrl.addTextChangedListener {
            toggleCreateBarcodeButton()
        }
    }

    private fun toggleCreateBarcodeButton() {
        parentActivity.isCreateBarcodeButtonEnabled = editTextTitle.isNotBlank() || editTextUrl.isNotBlank()
    }
}
