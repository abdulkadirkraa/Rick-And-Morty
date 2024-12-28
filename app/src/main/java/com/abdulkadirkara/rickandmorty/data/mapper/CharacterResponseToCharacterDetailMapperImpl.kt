package com.abdulkadirkara.rickandmorty.data.mapper

import com.abdulkadirkara.rickandmorty.data.remote.dto.CharacterResponse
import com.abdulkadirkara.rickandmorty.domain.mapper.MapperI
import com.abdulkadirkara.rickandmorty.domain.model.CharacterDetail
import com.abdulkadirkara.rickandmorty.util.toUserFriendlyDate
import javax.inject.Inject

class CharacterResponseToCharacterDetailMapperImpl @Inject constructor() : MapperI<CharacterResponse, CharacterDetail> {
    override fun map(input: CharacterResponse): CharacterDetail {
        return CharacterDetail(
            id = input.id,
            name = input.name,
            image = input.image,
            species = input.species,
            status = input.status,
            gender = input.gender,
            originName = input.origin.name,
            locationName = input.location.name,
            episodes = input.episode,
            createdAt = input.created.toUserFriendlyDate()
        )
    }
}