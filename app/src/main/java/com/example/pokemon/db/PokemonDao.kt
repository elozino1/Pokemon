package com.example.pokemon.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pokemon.models.PokemonResult

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(pokemon: PokemonResult) : Long

    @Query("SELECT * FROM favourites")
    fun retrieveSavedPokemon() : LiveData<List<PokemonResult>>

    @Delete
    suspend fun removeSavedPokemon(pokemon: PokemonResult)
}