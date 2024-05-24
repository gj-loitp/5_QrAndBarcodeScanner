package com.mckimquyen.barcodescanner.extension

fun Int?.orZero(): Int {
    return this ?: 0
}