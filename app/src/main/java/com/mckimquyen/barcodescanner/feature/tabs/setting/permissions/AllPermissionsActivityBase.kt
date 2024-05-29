package com.mckimquyen.barcodescanner.feature.tabs.setting.permissions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.extension.applySystemWindowInsets
import com.mckimquyen.barcodescanner.feature.ActivityBase
import kotlinx.android.synthetic.main.activity_all_permissions.*

class AllPermissionsActivityBase : ActivityBase() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AllPermissionsActivityBase::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_permissions)
        rootView.applySystemWindowInsets(applyTop = true, applyBottom = true)
        toolbar.setNavigationOnClickListener { finish() }
    }
}