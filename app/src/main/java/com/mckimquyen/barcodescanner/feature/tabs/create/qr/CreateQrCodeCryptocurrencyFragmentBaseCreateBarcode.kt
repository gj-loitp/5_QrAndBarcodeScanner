package com.mckimquyen.barcodescanner.feature.tabs.create.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.extension.isNotBlank
import com.mckimquyen.barcodescanner.extension.textString
import com.mckimquyen.barcodescanner.feature.tabs.create.FragmentBaseCreateBarcode
import com.mckimquyen.barcodescanner.model.schema.Cryptocurrency
import com.mckimquyen.barcodescanner.model.schema.Schema
import kotlinx.android.synthetic.main.f_create_qr_code_cryptocurrency.*

class CreateQrCodeCryptocurrencyFragmentBaseCreateBarcode : FragmentBaseCreateBarcode() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.f_create_qr_code_cryptocurrency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCryptocurrenciesSpinner()
        initAddressEditText()
        handleTextChanged()
    }

    override fun getBarcodeSchema(): Schema {
        val cryptocurrency = when (spinnerCryptocurrency.selectedItemPosition) {
            0 -> "bitcoin"
            1 -> "bitcoincash"
            2 -> "ethereum"
            3 -> "litecoin"
            4 -> "dash"
            else -> "bitcoin"
        }
        return Cryptocurrency(
            cryptocurrency = cryptocurrency,
            address = editTextAddress.textString,
            label = editTextLabel.textString,
            amount = editTextAmount.textString,
            message = editTextMessage.textString
        )
    }

    private fun initCryptocurrenciesSpinner() {
        spinnerCryptocurrency.adapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.fragment_create_qr_code_cryptocurrencies, R.layout.i_spinner
        ).apply {
            setDropDownViewResource(R.layout.i_spinner_dropdown)
        }
    }

    private fun initAddressEditText() {
        editTextAddress.requestFocus()
    }

    private fun handleTextChanged() {
        listOf(editTextAddress, editTextAmount, editTextLabel, editTextMessage).forEach { editText ->
            editText.addTextChangedListener { toggleCreateBarcodeButton() }
        }
    }

    private fun toggleCreateBarcodeButton() {
        parentActivity.isCreateBarcodeButtonEnabled = editTextAddress.isNotBlank()
    }
}