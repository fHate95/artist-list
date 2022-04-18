package io.fhate.network.model

import com.google.gson.annotations.SerializedName

data class ArtistResponse(
    @SerializedName("artists") val artist: ArtistsResponse
)