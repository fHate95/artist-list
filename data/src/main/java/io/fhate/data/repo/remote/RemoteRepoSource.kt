package io.fhate.data.repo.remote

import io.fhate.core.data.wrapper.LoadableData
import io.fhate.data.model.Artist
import kotlinx.coroutines.flow.Flow

interface RemoteRepoSource {

//    suspend fun getTestData(): Flow<LoadableData<List<Artist>>>
    suspend fun getArtists(networkAvailable: Boolean): Flow<LoadableData<List<Artist>>>

}