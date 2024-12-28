package com.abdulkadirkara.rickandmorty.di.mapper

import com.abdulkadirkara.rickandmorty.data.mapper.CharacterListMapper
import com.abdulkadirkara.rickandmorty.data.mapper.CharacterResponseToCharacterDetailMapperImpl
import com.abdulkadirkara.rickandmorty.data.mapper.LocationListMapperImpl
import com.abdulkadirkara.rickandmorty.data.remote.dto.CharacterResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.Result
import com.abdulkadirkara.rickandmorty.domain.mapper.ListMapper
import com.abdulkadirkara.rickandmorty.domain.mapper.MapperI
import com.abdulkadirkara.rickandmorty.domain.model.CharacterDetail
import com.abdulkadirkara.rickandmorty.domain.model.CharacterListItem
import com.abdulkadirkara.rickandmorty.domain.model.LocationListItem
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {
    @Binds
    @Singleton
    abstract fun bindCharacterListMapperImpl(
        characterListMapper: CharacterListMapper
    ) : ListMapper<CharacterResponse, CharacterListItem>

    @Binds
    @Singleton
    abstract fun bindCharacterDetailMapper(
        characterDetailMapperImpl: CharacterResponseToCharacterDetailMapperImpl
    ) : MapperI<CharacterResponse, CharacterDetail>

    @Binds
    @Singleton
    abstract fun bindLocationListMapperImpl(
        locationListMapperImpl: LocationListMapperImpl
    ) : ListMapper<Result, LocationListItem>
}