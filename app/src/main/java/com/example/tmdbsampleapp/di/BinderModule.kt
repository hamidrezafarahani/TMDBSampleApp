package com.example.tmdbsampleapp.di

import com.example.tmdbsampleapp.data.remote.RemoteDataSource
import com.example.tmdbsampleapp.data.remote.RemoteDataSourceImpl
import com.example.tmdbsampleapp.network.calladpter.LiveDataCallAdapterFactory
import com.example.tmdbsampleapp.data.repository.Repository
import com.example.tmdbsampleapp.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.CallAdapter

@Module
@InstallIn(SingletonComponent::class)
abstract class BinderModule {

    @Binds
    abstract fun bindCallAdapterFactory(factory: LiveDataCallAdapterFactory): CallAdapter.Factory

    @Binds
    abstract fun bindRemoteDataSource(remote: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    abstract fun bindRepo(repo: RepositoryImpl): Repository
}