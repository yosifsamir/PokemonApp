package com.example.pokemonapp.di

import android.app.Application
import androidx.room.Room
import com.example.pokemonapp.localdb.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ) = Room.databaseBuilder(app, PokemonDatabase::class.java, "saved_pokemon_data")
        .build()

    @Provides
    fun provideDao(db: PokemonDatabase) = db.getPokemonDao()
}