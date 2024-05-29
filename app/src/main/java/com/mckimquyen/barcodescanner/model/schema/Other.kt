package com.mckimquyen.barcodescanner.model.schema

import androidx.annotation.Keep

@Keep
class Other(val text: String) : Schema {
    override val schema = BarcodeSchema.OTHER
    override fun toFormattedText(): String = text
    override fun toBarcodeText(): String = text
}
