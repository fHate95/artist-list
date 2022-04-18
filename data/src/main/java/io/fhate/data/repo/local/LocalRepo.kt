package io.fhate.data.repo.local

import io.fhate.core.data.Errors
import io.fhate.core.data.wrapper.LoadableData
import io.fhate.data.model.Artist
import io.fhate.data.room.dao.ArtistDao
import io.fhate.network.executor.DefaultRequestExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocalRepo @Inject constructor(
    private val artistDao: ArtistDao
): LocalRepoSource {

    override suspend fun getCachedArtists(): Flow<LoadableData<List<Artist>>> {
        return flow {
            val artists = artistDao.getAll()
            if (artists.isNullOrEmpty()) {
                emit(LoadableData.Error(Errors.ERR_NOT_FOUND_IN_DB, "Not found in DB after connection error"))
            } else {
                emit(LoadableData.Success(artists))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun cacheArtists(artists: List<Artist>) {
        artistDao.insertAll(artists)
    }

}