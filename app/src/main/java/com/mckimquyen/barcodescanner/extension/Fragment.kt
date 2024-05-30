package com.mckimquyen.barcodescanner.extension

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import com.mckimquyen.barcodescanner.feature.common.dlg.DialogFragmentError

val Fragment.packageManager: PackageManager
    get() = requireContext().packageManager

fun Fragment.showError(error: Throwable?) {
    val errorDialog = DialogFragmentError.newInstance(requireContext(), error)
    errorDialog.show(childFragmentManager, "")
}
