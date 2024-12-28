package com.abdulkadirkara.rickandmorty.data.mapper

import com.abdulkadirkara.rickandmorty.data.remote.dto.Result
import com.abdulkadirkara.rickandmorty.domain.mapper.ListMapper
import com.abdulkadirkara.rickandmorty.domain.mapper.MapperI
import com.abdulkadirkara.rickandmorty.domain.model.LocationListItem
import javax.inject.Inject

class LocationListMapperImpl @Inject constructor(
    private val itemMapper: MapperI<Result, LocationListItem>
) : ListMapper<Result, LocationListItem> {
    override fun map(input: List<Result>?): List<LocationListItem> {
        return input?.map { itemMapper.map(it) } ?: emptyList()
    }
}