package io.fhate.data.repo.local

import io.fhate.core.data.wrapper.LoadableData
import io.fhate.data.model.Artist
import kotlinx.coroutines.flow.Flow

interface LocalRepoSource {

    suspend fun getCachedArtists(): Flow<LoadableData<List<Artist>>>
    suspend fun cacheArtists(artists: List<Artist>)

}