package io.fhate.data.room.dao

import androidx.room.*
import io.fhate.data.model.Artist

@Dao
interface ArtistDao {

    @Query("SELECT * FROM artist")
    fun getAll(): List<Artist>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg artists: Artist)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(artists: List<Artist>)

    @Delete
    fun delete(artist: Artist)

}