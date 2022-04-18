package io.fhate.artist_list.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.fhate.network.BuildConfig
import io.fhate.network.executor.DefaultRequestExecutor
import io.fhate.network.service.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RequestExecutorModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val retrofit = Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.API_ROOT).build()

        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDefaultRequestExecutor(@ApplicationContext context: Context): DefaultRequestExecutor {
        return DefaultRequestExecutor(context, provideApiService())
    }

}