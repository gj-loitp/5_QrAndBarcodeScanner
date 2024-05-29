package com.mckimquyen.barcodescanner.feature.tabs.setting.formats

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.di.settings
import com.mckimquyen.barcodescanner.extension.applySystemWindowInsets
import com.mckimquyen.barcodescanner.extension.unsafeLazy
import com.mckimquyen.barcodescanner.feature.ActivityBase
import com.mckimquyen.barcodescanner.usecase.SupportedBarcodeFormats
import com.google.zxing.BarcodeFormat
import kotlinx.android.synthetic.main.a_supported_formats.*

class SupportedFormatsActivityBase : ActivityBase(), FormatsAdapter.Listener {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SupportedFormatsActivityBase::class.java)
            context.startActivity(intent)
        }
    }

    private val formats by unsafeLazy { SupportedBarcodeFormats.FORMATS }
    private val formatSelection by unsafeLazy { formats.map(settings::isFormatSelected) }
    private val formatsAdapter by unsafeLazy { FormatsAdapter(this, formats, formatSelection) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_supported_formats)
        supportEdgeToEdge()
        initRecyclerView()
        handleToolbarBackClicked()
    }

    override fun onFormatChecked(format: BarcodeFormat, isChecked: Boolean) {
        settings.setFormatSelected(format, isChecked)
    }

    private fun supportEdgeToEdge() {
        rootView.applySystemWindowInsets(applyTop = true, applyBottom = true)
    }

    private fun initRecyclerView() {
        recyclerViewFormats.apply {
            layoutManager = LinearLayoutManager(this@SupportedFormatsActivityBase)
            adapter = formatsAdapter
        }
    }

    private fun handleToolbarBackClicked() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}