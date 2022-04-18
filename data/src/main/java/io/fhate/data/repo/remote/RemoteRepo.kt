package io.fhate.data.repo.remote

import io.fhate.core.data.Errors
import io.fhate.core.data.wrapper.LoadableData
import io.fhate.data.model.Artist
import io.fhate.network.executor.DefaultRequestExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteRepo @Inject constructor(
    private val defaultRequestExecutor: DefaultRequestExecutor
): RemoteRepoSource {

    override suspend fun getArtists(networkAvailable: Boolean): Flow<LoadableData<List<Artist>>> {
        // Prevent executing request if network is unavailable
        if (!networkAvailable) {
            return flow {
                emit(LoadableData.Error(Errors.ERR_CONNECTION, "Connection error"))
            }
        }
        return flow {
            val result = defaultRequestExecutor.getArtists()
            val artist = result.data

            if (artist != null) {
                val list = artist.map {
                    Artist(it)
                }
                emit(LoadableData.Success(list))
            } else {
                if (result is LoadableData.Loading) {
                    emit(LoadableData.Loading())
                } else if (result is LoadableData.Error) {
                    emit(LoadableData.Error(result.errorCode, result.errorMessage))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

}