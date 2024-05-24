package com.mckimquyen.barcodescanner.extension

fun Double?.orZero(): Double {
    return this ?: 0.0
}