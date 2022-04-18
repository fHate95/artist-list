package io.fhate.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImageBody(
    @SerializedName("#text") val text: String,
    @Expose val size: String
)