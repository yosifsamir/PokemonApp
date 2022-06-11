package com.example.pokemonapp.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokemonapp.data.PokemonDetails

@Database(entities = [PokemonDetails::class],version = 1,exportSchema = false)
@TypeConverters(DatabaseTypeConverters::class)
abstract class PokemonDatabase: RoomDatabase() {
    abstract fun getPokemonDao():PokemonDao

}