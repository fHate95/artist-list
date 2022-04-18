package io.fhate.artist_list.ui.home

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import io.fhate.artist_list.data.artist.ArtistModel
import io.fhate.core.tools.ext.loadFromUrl
import io.fhate.core.util.TextUtils

object HomeDataBindingAdapter {

    @JvmStatic @BindingAdapter("artistImage")
    fun artistImage(iv: ImageView, artist: ArtistModel) {

        iv.loadFromUrl(artist.findLargeImageUrlOrDefault(),
            onLoadingError = {

            },
            onLoadingFinished = {

            })
    }

    @JvmStatic @BindingAdapter("formattedListeners")
    fun formattedListeners(tv: TextView, listeners: Long) {
        tv.text = TextUtils.formatPrettyCount(listeners)
    }

}