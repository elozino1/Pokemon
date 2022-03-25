package com.example.pokemon.models


data class PokemonDetails(
    val abilities: List<Ability>,
    val base_experience: Int,
    val height: Int,
    val id: Int,
    val moves: List<Move>,
    val name: String,
    val species: Species,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int
)