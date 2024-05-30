package com.mckimquyen.barcodescanner.extension

fun Boolean?.orFalse(): Boolean {
    return this ?: false
}
