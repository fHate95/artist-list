package io.fhate.artist_list.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.fhate.data.room.converter.ImageConverter
import io.fhate.data.room.dao.ArtistDao
import io.fhate.data.room.db.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideArtistDao(@ApplicationContext context: Context): ArtistDao {
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "app-db")
            .build()

        return db.artistDao()
    }

}