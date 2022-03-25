package com.example.pokemon.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favourites"
)
data class PokemonResult(
    @PrimaryKey(autoGenerate = true)
    var tableId: Int? = null,
    val name: String,
    val url: String
)