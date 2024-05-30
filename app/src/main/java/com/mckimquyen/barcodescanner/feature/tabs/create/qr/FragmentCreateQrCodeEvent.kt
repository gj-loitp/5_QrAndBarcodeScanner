package com.mckimquyen.barcodescanner.feature.tabs.create.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.extension.textString
import com.mckimquyen.barcodescanner.feature.tabs.create.FragmentBaseCreateBarcode
import com.mckimquyen.barcodescanner.model.schema.Schema
import com.mckimquyen.barcodescanner.model.schema.VEvent
import kotlinx.android.synthetic.main.f_create_qr_code_vevent.*

class FragmentCreateQrCodeEvent : FragmentBaseCreateBarcode() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.f_create_qr_code_vevent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextTitle.requestFocus()
        parentActivity.isCreateBarcodeButtonEnabled = true
    }

    override fun getBarcodeSchema(): Schema {
        return VEvent(
            uid = editTextTitle.textString,
            organizer = editTextOrganizer.textString,
            summary = editTextSummary.textString,
            startDate = buttonDateTimeStart.dateTime,
            endDate = button_date_time_end.dateTime
        )
    }
}
