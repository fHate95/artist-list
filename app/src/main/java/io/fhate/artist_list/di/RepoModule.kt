package io.fhate.artist_list.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.fhate.data.repo.local.LocalRepo
import io.fhate.data.repo.local.LocalRepoSource
import io.fhate.data.repo.remote.RemoteRepo
import io.fhate.data.repo.remote.RemoteRepoSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    @Singleton
    abstract fun provideRemoteRepo(remoteRepo: RemoteRepo): RemoteRepoSource

    @Binds
    @Singleton
    abstract fun provideLocalRepo(localRepo: LocalRepo): LocalRepoSource

}