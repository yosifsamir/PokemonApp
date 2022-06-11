package com.example.pokemonapp.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokemonapp.data.PokemonDetails

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemonDetails: PokemonDetails)

    @Query("SELECT * FROM pokemon_table")
    suspend fun getAllSavedPokemon(): List<PokemonDetails>

    @Query("SELECT * FROM pokemon_table WHERE id LIKE :pokemonId")
    suspend fun getSavedPokemon(pokemonId: Int) : PokemonDetails

    @Query("DELETE FROM pokemon_table WHERE id LIKE :pokemonId")
    suspend fun deletePokemon(pokemonId: Int)
}