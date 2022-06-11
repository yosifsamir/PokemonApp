package com.example.pokemonapp.localdb

import androidx.room.TypeConverter
import com.example.pokemonapp.data.Stat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class DatabaseTypeConverters {

    @TypeConverter
    fun fromStats(stats: List<Stat>) : String {
        val gson = Gson()
        return gson.toJson(stats)
    }

    @TypeConverter
    fun toStats(statsString: String): List<Stat> {
        val listType: Type = object : TypeToken<List<Stat?>?>() {}.type
        return Gson().fromJson(statsString,listType)
    }

    @TypeConverter
    fun fromTypes(types: List<com.example.pokemonapp.data.Type>) : String {
        val gson = Gson()
        return gson.toJson(types)

    }

    @TypeConverter
    fun toTypes(typesString: String): List<com.example.pokemonapp.data.Type> {
        val listType: Type = object : TypeToken<List<com.example.pokemonapp.data.Type?>?>() {}.type
        return Gson().fromJson(typesString,listType)
    }
}