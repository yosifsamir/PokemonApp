package com.example.pokemonapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "pokemon_table"
)
data class PokemonDetails (val height: Int,
                           @PrimaryKey
                           val id: Int,
                           val name: String,
                           val stats: List<Stat>,
                           val types: List<Type>,
                           val weight: Int
): Serializable