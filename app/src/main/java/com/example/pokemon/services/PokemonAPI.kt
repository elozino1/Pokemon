package com.example.pokemon.services

import com.example.pokemon.models.PokemonDetails
import com.example.pokemon.models.PokemonResponse
import com.example.pokemon.utils.Constants.Companion.POKEMON_DISPLAY_LIMIT
import com.example.pokemon.utils.Constants.Companion.POKEMON_DISPLAY_OFFSET
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonAPI {

    @GET("pokemon")
    suspend fun fetchPokemon(
        @Query("offset") offset: Int = POKEMON_DISPLAY_OFFSET,
        @Query("limit") limit: Int = POKEMON_DISPLAY_LIMIT
    ) : Response<PokemonResponse>

    @GET("pokemon/{number}")
    suspend fun fetchPokemonDetails(
        @Path("number") number: Int
    ) : Response<PokemonDetails>
}