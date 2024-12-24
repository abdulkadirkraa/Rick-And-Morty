package com.abdulkadirkara.rickandmorty.di

import com.abdulkadirkara.rickandmorty.data.datasource.RemoteDataSource
import com.abdulkadirkara.rickandmorty.data.datasource.RemoteDataSourceImpl
import com.abdulkadirkara.rickandmorty.data.repository.RickAndMortyRepositoryImpl
import com.abdulkadirkara.rickandmorty.domain.repository.RickAndMortyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindRickAndMortyRepository(rickAndMortyRepositoryImpl: RickAndMortyRepositoryImpl): RickAndMortyRepository

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

}