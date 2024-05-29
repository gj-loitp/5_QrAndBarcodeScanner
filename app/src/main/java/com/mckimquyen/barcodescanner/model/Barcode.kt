package com.mckimquyen.barcodescanner.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mckimquyen.barcodescanner.model.schema.BarcodeSchema
import com.mckimquyen.barcodescanner.usecase.BarcodeDatabaseTypeConverter
import com.google.zxing.BarcodeFormat
import java.io.Serializable

@Keep
@Entity(tableName = "codes")
@TypeConverters(BarcodeDatabaseTypeConverter::class)
data class Barcode(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String? = null,
    val text: String,
    val formattedText: String,
    val format: BarcodeFormat,
    val schema: BarcodeSchema,
    val date: Long,
    val isGenerated: Boolean = false,
    val isFavorite: Boolean = false,
    val errorCorrectionLevel: String? = null,
    val country: String? = null,
) : Serializable
