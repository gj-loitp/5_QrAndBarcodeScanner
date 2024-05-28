package com.mckimquyen.barcodescanner.feature.tabs.create.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.extension.encodeBase32
import com.mckimquyen.barcodescanner.extension.isNotBlank
import com.mckimquyen.barcodescanner.extension.textString
import com.mckimquyen.barcodescanner.extension.toHmacAlgorithm
import com.mckimquyen.barcodescanner.feature.tabs.create.BaseCreateBarcodeFragment
import com.mckimquyen.barcodescanner.model.schema.OtpAuth
import com.mckimquyen.barcodescanner.model.schema.Schema
import dev.turingcomplete.kotlinonetimepassword.RandomSecretGenerator
import kotlinx.android.synthetic.main.f_create_qr_code_otp.*
import java.util.*

class CreateQrCodeOtpFragment : BaseCreateBarcodeFragment() {
    private val randomGenerator = RandomSecretGenerator()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.f_create_qr_code_otp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOtpTypesSpinner()
        initAlgorithmsSpinner()
        initEditTexts()
        initGenerateRandomSecretButton()
        showRandomSecret()
    }

    override fun getBarcodeSchema(): Schema {
        return OtpAuth(
            type = spinnerOptTypes.selectedItem?.toString()?.toLowerCase(Locale.ENGLISH),
            algorithm = spinnerAlgorithms.selectedItem?.toString(),
            label = if (editTextIssuer.isNotBlank()) {
                "${editTextIssuer.textString}:${editTextAccount.textString}"
            } else {
                editTextAccount.textString
            },
            issuer = editTextIssuer.textString,
            digits = editTextDigits.textString.toIntOrNull(),
            period = editTextPeriod.textString.toLongOrNull(),
            counter = editTextCounter.textString.toLongOrNull(),
            secret = editTextSecret.textString
        )
    }

    private fun initOtpTypesSpinner() {
        spinnerOptTypes.adapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.fragment_create_qr_code_otp_types, R.layout.i_spinner
        ).apply {
            setDropDownViewResource(R.layout.i_spinner_dropdown)
        }

        spinnerOptTypes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                textInputLayoutCounter.isVisible = position == 0
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun initAlgorithmsSpinner() {
        spinnerAlgorithms.adapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.fragment_create_qr_code_otp_algorithms, R.layout.i_spinner
        ).apply {
            setDropDownViewResource(R.layout.i_spinner_dropdown)
        }
    }

    private fun initEditTexts() {
        editTextAccount.addTextChangedListener { toggleCreateBarcodeButton() }
        editTextSecret.addTextChangedListener { toggleCreateBarcodeButton() }
        editTextPeriod.addTextChangedListener { toggleCreateBarcodeButton() }
        editTextCounter.addTextChangedListener { toggleCreateBarcodeButton() }
    }

    private fun initGenerateRandomSecretButton() {
        buttonGenerateRandomSecret.setOnClickListener {
            showRandomSecret()
        }
    }

    private fun toggleCreateBarcodeButton() {
        val isHotp = spinnerOptTypes.selectedItemPosition == 0
        val areGeneralFieldsNotBlank = editTextAccount.isNotBlank() && editTextSecret.isNotBlank()
        val areHotpFieldsNotBlank = editTextCounter.isNotBlank() && editTextPeriod.isNotBlank()
        parentActivity.isCreateBarcodeButtonEnabled = areGeneralFieldsNotBlank && (isHotp.not() || isHotp && areHotpFieldsNotBlank)
    }

    private fun showRandomSecret() {
        editTextSecret.setText(generateRandomSecret())
    }

    private fun generateRandomSecret(): String {
        val algorithm = spinnerAlgorithms.selectedItem?.toString().toHmacAlgorithm()
        val secret = randomGenerator.createRandomSecret(algorithm)
        return secret.encodeBase32()
    }
}