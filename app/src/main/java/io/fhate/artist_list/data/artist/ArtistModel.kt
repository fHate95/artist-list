package io.fhate.artist_list.data.artist

import io.fhate.core.ui.adapter.ListItemViewModel
import io.fhate.data.model.Artist

data class ArtistModel(
    val id: String,
    val name: String,
    val url: String,
    val listeners: Long,
    val images: List<ImageModel> = listOf()
): ListItemViewModel() {

    constructor(artist: Artist): this(
        id = artist.id,
        name = artist.name,
        url = artist.url,
        listeners = artist.listeners,
        images = artist.images.map { ImageModel(it.text, it.size) }
    )

    fun findLargeImageUrlOrDefault(): String {
        val image = images.find { it.size == "large" }
        return image?.text ?: if (images.isNotEmpty()) {
            images.first().text
        } else ""
    }

}