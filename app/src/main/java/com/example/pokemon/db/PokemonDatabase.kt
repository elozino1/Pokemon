package com.example.pokemon.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokemon.models.PokemonResult

@Database(
    entities = [PokemonResult::class],
    version = 1
)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun getPokemonDao() : PokemonDao

    companion object {
        @Volatile
        private var instance: PokemonDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {instance = it}
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PokemonDatabase::class.java,
                "pokemon_database.db"
            ).build()
    }

}