package com.example.pokemonapp.api

import com.example.pokemonapp.data.PokemonDetails
import com.example.pokemonapp.data.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PokeApi {
    @GET("pokemon")
    suspend fun getAllPokemon(
        @Query("limit")
        limit: Int,
        @Query("offset")
        offset: Int
    ) : PokemonListResponse

    @GET("pokemon/{pokemonName}")
    suspend fun getPokemonDetails(
        @Path("pokemonName")
        pokemonName: String
    ) : PokemonDetails


    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
        const val PAGING_STARTING_OFFSET_INDEX = 0
    }
}