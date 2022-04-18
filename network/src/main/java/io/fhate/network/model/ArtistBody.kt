package io.fhate.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ArtistBody(
    @SerializedName("mbid") val id: String,
    @Expose val name: String,
    @Expose val url: String,
    @Expose val listeners: Long,
    @Expose val image: List<ImageBody> = listOf()
)
