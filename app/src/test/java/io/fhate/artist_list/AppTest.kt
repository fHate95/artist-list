package io.fhate.artist_list

import io.fhate.data.model.Artist
import io.fhate.data.model.Image
import org.junit.Test
import org.junit.Assert.*
import java.util.*

class AppTest {

    @Test
    fun artist_findLargeImageUrlOrDefault() {
        val withLargeImageArtist = Artist(
            id = UUID.randomUUID().toString(),
            name = "Name",
            url = "",
            listeners = 0,
            images = listOf(
                Image("smallUrl", "small"),
                Image("mediumUrl", "medium"),
                Image("largeUrl", "large"),
                Image("megaUrl", "mega")
            )
        )
        val noLargeImageArtist = Artist(
            id = UUID.randomUUID().toString(),
            name = "Name",
            url = "",
            listeners = 0,
            images = listOf(
                Image("smallUrl", "small"),
                Image("mediumUrl", "medium"),
                Image("megaUrl", "mega")
            )
        )
        val emptyImageArtist = Artist(
            id = UUID.randomUUID().toString(),
            name = "Name",
            url = "",
            listeners = 0,
            images = listOf()
        )
        val onlyOneImageArtist = Artist(
            id = UUID.randomUUID().toString(),
            name = "Name",
            url = "",
            listeners = 0,
            images = listOf(
                Image("mediumUrl", "medium")
            )
        )

        assertEquals("largeUrl", withLargeImageArtist.findLargeImageUrlOrDefault())
        assertEquals("smallUrl", noLargeImageArtist.findLargeImageUrlOrDefault())
        assertEquals("", emptyImageArtist.findLargeImageUrlOrDefault())
        assertEquals("mediumUrl", onlyOneImageArtist.findLargeImageUrlOrDefault())

    }

}