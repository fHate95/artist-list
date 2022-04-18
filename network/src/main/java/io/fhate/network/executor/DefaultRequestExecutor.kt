package io.fhate.network.executor

import android.content.Context
import android.util.Log
import io.fhate.core.data.wrapper.LoadableData
import io.fhate.network.BuildConfig
import io.fhate.network.R
import io.fhate.network.model.ArtistBody
import io.fhate.network.service.ApiService
import okhttp3.OkHttpClient
import java.net.UnknownHostException
import javax.inject.Inject

/* Default request executor implementation */
class DefaultRequestExecutor @Inject constructor(
    private val context: Context,
    private val service: ApiService
): RequestExecutor {

    /* Get artist list call */
    fun getArtists(): LoadableData<List<ArtistBody>> {
        try {
            val response = service.getArtists(BuildConfig.API_KEY).execute()
            log(response.toString())
            if (response.isSuccessful) {
                response.body()?.let {
                    return LoadableData.Success(it.artist.artists)
                }
            }
            return LoadableData.Error(response.code(), response.message())
        } catch (e: Exception) {
            //UnknownHostException occurs when device is offline (mobile and wifi data is off)
                //Tested on Samsung A51
            if (e is UnknownHostException) {
                return LoadableData.Error(522, context.getString(R.string.error_connection))
            }
            return LoadableData.Error(-1, e.message)
        }
    }

}