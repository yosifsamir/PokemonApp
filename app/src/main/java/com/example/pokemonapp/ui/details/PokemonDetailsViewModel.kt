package com.example.pokemonapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.data.Pokemon
import com.example.pokemonapp.data.PokemonDetails
import com.example.pokemonapp.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(private val repository: PokemonRepository) : ViewModel() {

    private var _result = MutableLiveData<PokemonDetails>()
    val pokemonLiveData: LiveData<PokemonDetails> = _result

    var pokemon:Pokemon ? =null
    var pokemonId=0
    var isFetchFromRemote=true


    fun fetchData() {
        if (isFetchFromRemote == true) {
            getPokemonDetails()
        } else {
            getLocalPokemon()
        }
    }

    private fun getPokemonDetails() = viewModelScope.launch {
        _result.value = pokemon?.name?.let { repository.getPokemonDetails(it) }
    }

    private fun getLocalPokemon() = viewModelScope.launch {
        if (pokemonId != 0) {
            _result.value = pokemonId.let { repository.getSavedPokemon(it) }
        }
    }


    fun savePokemon(pokemon: PokemonDetails) {
        viewModelScope.launch {
            repository.upsertPokemon(pokemon)
        }
    }

}