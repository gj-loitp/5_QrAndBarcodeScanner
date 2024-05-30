package com.mckimquyen.barcodescanner.feature.tabs.create

import androidx.fragment.app.Fragment
import com.mckimquyen.barcodescanner.extension.*
import com.mckimquyen.barcodescanner.model.Contact
import com.mckimquyen.barcodescanner.model.schema.Other
import com.mckimquyen.barcodescanner.model.schema.Schema

abstract class FragmentBaseCreateBarcode : Fragment() {
    protected val parentActivity by unsafeLazy {
        requireActivity() as ActivityCreateBarcode
    }

    open val latitude: Double? = null
    open val longitude: Double? = null

    open fun getBarcodeSchema(): Schema = Other("")
    open fun showPhone(phone: String) {}
    open fun showContact(contact: Contact) {}
    open fun showLocation(latitude: Double?, longitude: Double?) {}
}
