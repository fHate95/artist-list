package io.fhate.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Image(
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "size") val size: String
)