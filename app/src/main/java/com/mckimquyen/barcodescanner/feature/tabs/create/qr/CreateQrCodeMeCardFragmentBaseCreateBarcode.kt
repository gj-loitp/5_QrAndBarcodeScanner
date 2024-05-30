package com.mckimquyen.barcodescanner.feature.tabs.create.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.extension.textString
import com.mckimquyen.barcodescanner.feature.tabs.create.FragmentBaseCreateBarcode
import com.mckimquyen.barcodescanner.model.Contact
import com.mckimquyen.barcodescanner.model.schema.MeCard
import com.mckimquyen.barcodescanner.model.schema.Schema
import kotlinx.android.synthetic.main.f_create_qr_code_mecard.*

class CreateQrCodeMeCardFragmentBaseCreateBarcode : FragmentBaseCreateBarcode() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.f_create_qr_code_mecard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextFirstName.requestFocus()
        parentActivity.isCreateBarcodeButtonEnabled = true
    }

    override fun getBarcodeSchema(): Schema {
        return MeCard(
            firstName = editTextFirstName.textString,
            lastName = editTextLastName.textString,
            email = editTextEmail.textString,
            phone = editTextPhone.textString
        )
    }

    override fun showContact(contact: Contact) {
        editTextFirstName.setText(contact.firstName)
        editTextLastName.setText(contact.lastName)
        editTextEmail.setText(contact.email)
        editTextPhone.setText(contact.phone)
    }
}