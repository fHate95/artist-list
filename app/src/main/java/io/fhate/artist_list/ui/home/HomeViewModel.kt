package io.fhate.artist_list.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.fhate.core.data.Errors
import io.fhate.core.data.wrapper.LoadableData
import io.fhate.data.model.Artist
import io.fhate.data.repo.local.LocalRepo
import io.fhate.data.repo.remote.RemoteRepo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteRepo: RemoteRepo,
    private val localRepo: LocalRepo,
    private val coroutineContext: CoroutineContext,
    private val glide: RequestManager
): ViewModel() {

    val artistsData = MutableLiveData<LoadableData<List<Artist>>>()
    val errorShown = MutableLiveData(false)
    val pBarShown = MutableLiveData(false)
    val networkAvailable = MutableLiveData(true)

    fun getArtists() {
        artistsData.value = LoadableData.Loading()
        viewModelScope.launch(coroutineContext) {
            remoteRepo.getArtists(networkAvailable.value!!).collect {
                when (it) {
                    is LoadableData.Error -> {
                        //try to restore stored data
                        if (it.errorCode == Errors.ERR_CONNECTION) {
                            localRepo.getCachedArtists().collect { data ->
                                artistsData.postValue(data)
                            }
                        } else {
                            artistsData.postValue(it)
                        }
                    }
                    is LoadableData.Success -> {
                        localRepo.cacheArtists(it.data?: listOf())
                        it.data?.let { list ->
                            // Preload images
                            for (artist in list) {
                                glide.load(artist.findLargeImageUrlOrDefault()).preload()
                            }
                        }
                        artistsData.postValue(it)
                    }
                    else -> {
                        //ignore
                    }
                }
            }
        }
    }

}