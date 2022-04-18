package io.fhate.artist_list.ui.home

import android.net.ConnectivityManager
import android.net.Network
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.fhate.artist_list.R
import io.fhate.artist_list.data.artist.ArtistDiffCallback
import io.fhate.artist_list.data.artist.ArtistModel
import io.fhate.artist_list.databinding.ActivityHomeBinding
import io.fhate.core.data.Errors
import io.fhate.core.data.wrapper.LoadableData
import io.fhate.core.tools.ext.*
import io.fhate.core.ui.adapter.GenericAdapter
import io.fhate.core.ui.base.BaseViewModelActivity
import io.fhate.core.util.ThemeUtils
import io.fhate.data.model.Artist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeActivity: BaseViewModelActivity<ActivityHomeBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    private lateinit var adapter: GenericAdapter<ArtistModel>

    private val viewModel: HomeViewModel by viewModels()

    override fun observeViewModel() {
        observe(viewModel.artistsData, ::onGetArtists)
        observe(viewModel.errorShown, ::onErrorShown)
        observe(viewModel.pBarShown, ::onProgressBarShown)
    }

    override fun init() {
        setTheme(R.style.AppTheme)
        ThemeUtils.applySystemBarsColor(this, R.color.white, R.color.colorBackground)
        setupToolbar()
        setupActions()
        setupRV()

        viewModel.getArtists()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
    }

    private fun setupRV() = with(binding) {
        adapter = GenericAdapter<ArtistModel>(arrayOf(R.layout.rv_gen_artist_item), R.anim.rv_item_appear_animation)
            .apply {
                setOnListItemViewClickListener(object: GenericAdapter.ClickListener {
                    override fun onClick(view: View, position: Int) {
                        this@HomeActivity.openUrl(adapter.getItem(position).url)
                    }
                })
            }
        rv.layoutManager = GridLayoutManager(this@HomeActivity, 3, GridLayoutManager.VERTICAL, false)
        rv.setHasFixedSize(true)
        rv.adapter = adapter
    }

    private fun setupActions() = with(binding) {
        srLayout.setOnRefreshListener {
            viewModel.getArtists()
        }
        errorView.setButtonClickListener {
            viewModel.getArtists()
        }
        registerNetworkCallback(object: ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                viewModel.networkAvailable.postValue(true)
            }

            override fun onLost(network: Network) {
                viewModel.networkAvailable.postValue(false)
            }
        })
    }

    private fun onGetArtists(result: LoadableData<List<Artist>>) {
        when (result) {
            is LoadableData.Success -> {
                viewModel.pBarShown.value = false
                viewModel.errorShown.value = false
                // Run mapping operation in coroutine
                CoroutineScope(Dispatchers.Default).launch {
                    val adapterData = result.data?.map {
                        ArtistModel(it)
                    }
                    ui {
                        delayAction(250) { // Skip ui delay TODO: workaround this solution?
                            adapter.update(
                                adapterData,
                                ArtistDiffCallback(adapter.getItems(), adapterData?: listOf()))
                            binding.srLayout.isRefreshing = false
                            binding.errorView.hide()
                        }
                    }
                }
            }
            is LoadableData.Error -> {
                // Caught errors [522: connection, 4040: not found in cache]
                if (result.errorCode == Errors.ERR_CONNECTION || result.errorCode == Errors.ERR_NOT_FOUND_IN_DB) {
                    viewModel.errorShown.value = true
                    viewModel.pBarShown.value = false
                    binding.srLayout.isRefreshing = false
                } else {
                    // Something goes wrong
                    Toast.makeText(this, "Error: ${result.errorMessage}", Toast.LENGTH_SHORT).show()
                }
            }
            is LoadableData.Loading -> {
                viewModel.pBarShown.value = true
            }
        }
    }

    private fun onErrorShown(value: Boolean) {
        binding.errorView.apply {
            if (value) show() else hide()
        }
    }

    private fun onProgressBarShown(value: Boolean) {
        binding.pBar.apply {
            if (value) show{} else hide(duration = 200)
        }
    }

}