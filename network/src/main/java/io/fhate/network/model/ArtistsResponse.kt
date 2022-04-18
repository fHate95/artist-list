package io.fhate.network.model

import com.google.gson.annotations.SerializedName

data class ArtistsResponse(
    @SerializedName("artist") val artists: List<ArtistBody> = listOf()
)