package com.abdulkadirkara.rickandmorty.domain.mapper

import com.abdulkadirkara.rickandmorty.data.remote.dto.CharacterResponse
import com.abdulkadirkara.rickandmorty.domain.model.CharacterDetail
import com.abdulkadirkara.rickandmorty.domain.model.CharacterListItem
import com.abdulkadirkara.rickandmorty.domain.model.LocationDetail
import com.abdulkadirkara.rickandmorty.domain.model.LocationListItem
import com.abdulkadirkara.rickandmorty.data.remote.dto.Result
import com.abdulkadirkara.rickandmorty.util.toUserFriendlyDate

object Mapper {

    // Ana ekran için basit karakter modeli dönüşümü
    fun CharacterResponse.toCharacterListItem(): CharacterListItem {
        return CharacterListItem(
            id = id,
            name = name,
            image = image,
            status = status
        )
    }

    // Detay ekranı için detaylı karakter modeli dönüşümü
    fun CharacterResponse.toCharacterDetail(): CharacterDetail {
        return CharacterDetail(
            id = id,
            name = name,
            image = image,
            species = species,
            status = status,
            gender = gender,
            originName = origin.name,
            locationName = location.name,
            episodes = episode,
            createdAt = this.created.toUserFriendlyDate()
        )
    }

    // Ana ekran için basit konum modeli dönüşümü
    fun Result.toLocationListItem(): LocationListItem {
        return LocationListItem(
            id = id,
            name = name,
            residentsCount = residents.size
        )
    }

    // Detay ekranı için detaylı konum modeli dönüşümü
    fun Result.toLocationDetail(): LocationDetail {
        return LocationDetail(
            id = id,
            name = name,
            dimension = dimension,
            residents = residents,
            type = type,
        )
    }
}