package com.example.pokemonapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.pokemonapp.api.PokeApi
import com.example.pokemonapp.data.PokemonDetails
import com.example.pokemonapp.datasource.PokemonDataSource
import com.example.pokemonapp.localdb.PokemonDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(private val pokeApi:PokeApi
                                            ,private val db: PokemonDatabase) {

    fun getAllPokemon() = Pager(
        config = PagingConfig(
            pageSize = 50,  //
            maxSize = 1200,  // there is an animation with low page size like 50 and maxSize
            enablePlaceholders = false
        ),
        pagingSourceFactory = { PokemonDataSource(
            pokeApi
        ) }
    ).liveData

    suspend fun getPokemonDetails(pokemonName: String) =
        pokeApi.getPokemonDetails(pokemonName = pokemonName)


    //Local functions on Room database
    suspend fun upsertPokemon(pokemon: PokemonDetails) {
        db.getPokemonDao().insertPokemon(pokemon)
    }

    suspend fun getAllSavedPokemon() =
        db.getPokemonDao().getAllSavedPokemon()

    suspend fun getSavedPokemon(pokemonId: Int) =
        db.getPokemonDao().getSavedPokemon(pokemonId)


    suspend fun deletePokemon(id: Int) {
        db.getPokemonDao().deletePokemon(id)
    }

}