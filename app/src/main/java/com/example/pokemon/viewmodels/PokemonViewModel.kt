package com.example.pokemon.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.models.PokemonResponse
import com.example.pokemon.repositories.PokemonRepository
import com.example.pokemon.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class PokemonViewModel(
    val pokemonRepository: PokemonRepository
) : ViewModel() {

    val allPokemon: MutableLiveData<Resource<PokemonResponse>> = MutableLiveData()
    var limit = 20

    init {
        fetchPokemon(0)
    }

    fun fetchPokemon(offset: Int) = viewModelScope.launch {
        allPokemon.postValue(Resource.Loading())
        val response = pokemonRepository.fetchPokemon(offset, limit)
        allPokemon.postValue(handlePokemonResponse(response))
    }

    private fun handlePokemonResponse(response: Response<PokemonResponse>) : Resource<PokemonResponse> {
        if(response.isSuccessful) {
            response.body()?.let { responseResponse ->
                return Resource.Success(responseResponse)
            }
        }
        return Resource.Error(response.message())
    }
}