package com.example.pokemon.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.models.PokemonDetails
import com.example.pokemon.repositories.PokemonRepository
import com.example.pokemon.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class PokemonDetailsViewModel(
    val pokemonRepository: PokemonRepository
) : ViewModel() {

    val pokemonInfo: MutableLiveData<Resource<PokemonDetails>> = MutableLiveData()

    private fun fetchPokemonDetails(pokemonId: Int) = viewModelScope.launch {
        pokemonInfo.postValue(Resource.Loading())
        val response = pokemonRepository.fetchPokemonDetails(pokemonId)
        pokemonInfo.postValue(handlePokemonDetailsResponse(response))
    }

    private fun handlePokemonDetailsResponse(response: Response<PokemonDetails>) : Resource<PokemonDetails> {
        if(response.isSuccessful) {
            response.body()?.let { responseResponse ->
                return Resource.Success(responseResponse)
            }
        }
        return Resource.Error(response.message())
    }
}