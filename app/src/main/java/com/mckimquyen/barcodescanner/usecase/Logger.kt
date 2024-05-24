package com.mckimquyen.barcodescanner.usecase

import android.util.Log
import com.mckimquyen.barcodescanner.BuildConfig

object Logger {
    var isEnabled = BuildConfig.ERROR_REPORTS_ENABLED_BY_DEFAULT

    fun log(error: Throwable) {
        if (isEnabled) {
            Log.e("roy93~", "e $error")
        }
    }
}