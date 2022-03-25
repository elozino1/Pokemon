package com.example.pokemon.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pokemon.R
import com.example.pokemon.databinding.ActivityMainBinding
import com.example.pokemon.db.PokemonDatabase
import com.example.pokemon.repositories.PokemonRepository
import com.example.pokemon.viewmodels.PokemonViewModel
import com.example.pokemon.viewmodels.PokemonViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: PokemonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokemonRepository = PokemonRepository(PokemonDatabase(this))
        val pokemonViewModelProviderFactory = PokemonViewModelProviderFactory(pokemonRepository)
        viewModel = ViewModelProvider(this, pokemonViewModelProviderFactory).get(PokemonViewModel::class.java)

        val bottomNav = binding.bottomNavigation
        bottomNav.setupWithNavController(findNavController(R.id.nav_host))
    }

    override fun onDestroy() {
        _binding = null
    }
}