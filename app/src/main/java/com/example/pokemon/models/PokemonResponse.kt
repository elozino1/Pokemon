package com.example.pokemon.models

data class PokemonResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<PokemonResult>
)