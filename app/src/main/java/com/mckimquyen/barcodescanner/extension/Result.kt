package com.mckimquyen.barcodescanner.extension

import com.mckimquyen.barcodescanner.model.Barcode
import com.google.zxing.Result

fun Result.equalTo(barcode: Barcode?): Boolean {
    return barcodeFormat == barcode?.format && text == barcode?.text
}
