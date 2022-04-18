package io.fhate.data.model

import androidx.room.*
import io.fhate.core.ui.adapter.ListItemViewModel
import io.fhate.network.model.ArtistBody

@Entity
data class Artist(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "listeners") val listeners: Long,
    @ColumnInfo(name = "images") val images: List<Image> = listOf()
) {

    @Ignore
    constructor(artistBody: ArtistBody): this(
        id = artistBody.id,
        name = artistBody.name,
        url = artistBody.url,
        listeners = artistBody.listeners,
        images = artistBody.image.map { Image(it.text, it.size) }
    )

    fun findLargeImageUrlOrDefault(): String {
        val image = images.find { it.size == "large" }
        return image?.text ?: if (images.isNotEmpty()) {
            images.first().text
        } else ""
    }

}