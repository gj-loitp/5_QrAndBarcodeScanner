package com.mckimquyen.barcodescanner.feature

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mckimquyen.barcodescanner.di.rotationHelper

abstract class ActivityBase : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        rotationHelper.lockCurrentOrientationIfNeeded(this)
    }

    override fun attachBaseContext(context: Context) {
        val override = Configuration(context.resources.configuration)
        override.fontScale = 1.0f
        applyOverrideConfiguration(override)
        super.attachBaseContext(context)
    }
}
