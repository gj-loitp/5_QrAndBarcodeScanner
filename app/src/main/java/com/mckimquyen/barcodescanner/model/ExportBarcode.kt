package com.mckimquyen.barcodescanner.model

import androidx.annotation.Keep
import androidx.room.TypeConverters
import com.mckimquyen.barcodescanner.usecase.BarcodeDatabaseTypeConverter
import com.google.zxing.BarcodeFormat

@Keep
@TypeConverters(BarcodeDatabaseTypeConverter::class)
data class ExportBarcode(
    val date: Long,
    val format: BarcodeFormat,
    val text: String,
)
