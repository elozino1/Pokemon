package com.example.pokemon.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokemon.repositories.PokemonRepository

class PokemonViewModelProviderFactory(
    val pokemonRepository: PokemonRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PokemonViewModel(pokemonRepository) as T
    }
}