package com.mckimquyen.barcodescanner.extension

fun Long?.orZero(): Long {
    return this ?: 0L
}