package com.example.pokemonapp.data


data class PokemonListResponse(val count: Int,
                               val next: String,
                               val previous: String,
                               val results: List<Pokemon>)