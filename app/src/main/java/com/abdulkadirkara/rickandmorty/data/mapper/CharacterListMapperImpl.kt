package com.abdulkadirkara.rickandmorty.data.mapper

import com.abdulkadirkara.rickandmorty.data.remote.dto.CharacterResponse
import com.abdulkadirkara.rickandmorty.domain.mapper.ListMapper
import com.abdulkadirkara.rickandmorty.domain.mapper.MapperI
import com.abdulkadirkara.rickandmorty.domain.model.CharacterListItem
import javax.inject.Inject

class CharacterListMapper @Inject constructor(
    private val itemMapper: MapperI<CharacterResponse, CharacterListItem>
) : ListMapper<CharacterResponse, CharacterListItem> {
    override fun map(input: List<CharacterResponse>?): List<CharacterListItem> {
        return input?.map { itemMapper.map(it) } ?: emptyList()
    }
}