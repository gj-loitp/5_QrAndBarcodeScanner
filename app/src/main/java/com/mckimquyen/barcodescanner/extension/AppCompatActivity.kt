package com.mckimquyen.barcodescanner.extension

import androidx.appcompat.app.AppCompatActivity
import com.mckimquyen.barcodescanner.feature.common.dlg.DialogFragmentError

fun AppCompatActivity.showError(error: Throwable?) {
    val errorDialog =
        DialogFragmentError.newInstance(
            this,
            error
        )
    errorDialog.show(supportFragmentManager, "")
}
