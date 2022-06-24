package com.example.pokemon.repositories

import com.example.pokemon.db.PokemonDatabase
import com.example.pokemon.services.RetrofitInstance

class PokemonRepository(
    val db: PokemonDatabase
) {

    suspend fun fetchPokemon(offset: Int, limit: Int) =
        RetrofitInstance.api.fetchPokemon(offset, limit)

    suspend fun fetchPokemonDetails(number: Int) =
        RetrofitInstance.api.fetchPokemonDetails(number)


}