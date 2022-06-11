package com.example.pokemonapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.pokemonapp.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonMainViewModel @Inject constructor(private val repository: PokemonRepository): ViewModel() {

    val pokemonListLive = repository.getAllPokemon().cachedIn(viewModelScope)

}