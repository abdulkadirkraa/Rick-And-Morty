package com.abdulkadirkara.rickandmorty.di.coroutines

import javax.inject.Qualifier

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class RickAndMortyDispatchers(val type: DispatcherType)

enum class DispatcherType {
    Main, Io, Default, Unconfined
}