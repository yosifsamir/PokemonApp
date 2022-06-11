package com.example.pokemonapp.ui.mypokemons


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.data.PokemonDetails
import com.example.pokemonapp.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPokemonViewModel @Inject constructor(private val repository:PokemonRepository) : ViewModel() {
    private var _result=MutableLiveData<List<PokemonDetails>>()
    val pokemonLiveData = _result

    fun deletePokemon(pokemonId: Int) {
        viewModelScope.launch {
            repository.deletePokemon(pokemonId)
        }
    }


    fun getAllSavedPokemon(){
        viewModelScope.launch {
            _result.value=repository.getAllSavedPokemon()
        }
    }
}