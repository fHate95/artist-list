package io.fhate.network.service

import io.fhate.network.model.ArtistResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("?method=chart.gettopartists&format=json")
    fun getArtists(@Query("api_key") apiKey: String): Call<ArtistResponse>

}