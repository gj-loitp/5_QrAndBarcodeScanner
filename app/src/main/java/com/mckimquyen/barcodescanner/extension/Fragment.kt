package com.mckimquyen.barcodescanner.extension

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import com.mckimquyen.barcodescanner.feature.common.dialog.ErrorDialogFragment

val Fragment.packageManager: PackageManager
    get() = requireContext().packageManager

fun Fragment.showError(error: Throwable?) {
    val errorDialog = ErrorDialogFragment.newInstance(requireContext(), error)
    errorDialog.show(childFragmentManager, "")
}
