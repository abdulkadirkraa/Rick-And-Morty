package com.abdulkadirkara.rickandmorty.domain.mapper

interface MapperI <I, O> {
    fun map(input: I): O
}
