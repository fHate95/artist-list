package io.fhate.data.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.fhate.data.model.Artist
import io.fhate.data.room.converter.ImageConverter
import io.fhate.data.room.dao.ArtistDao

@Database(entities = [Artist::class], version = 1)
@TypeConverters(ImageConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun artistDao(): ArtistDao

}