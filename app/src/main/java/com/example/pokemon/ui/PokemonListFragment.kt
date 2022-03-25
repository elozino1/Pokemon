package com.example.pokemon.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemon.adapters.PokemonListAdapter
import com.example.pokemon.databinding.FragmentPokemonListBinding
import com.example.pokemon.utils.Resource
import com.example.pokemon.viewmodels.PokemonViewModel

class PokemonListFragment : Fragment() {

    lateinit var viewModel: PokemonViewModel
    lateinit var pokemonListAdapter: PokemonListAdapter

    private var _binding: FragmentPokemonListBinding? = null
    private val binding get() = _binding!!

    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPokemonListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()

        viewModel.allPokemon.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { pokemonResponse ->
                        pokemonListAdapter.differ.submitList(pokemonResponse.results)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { errorMessage ->
                        Toast.makeText(requireContext(), "Error occurred: $errorMessage", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        progressBar = binding.progressBar
        progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        pokemonListAdapter = PokemonListAdapter()
        val pokemonRecyclerView = binding.pokemonRecyclerview
        pokemonRecyclerView.adapter = pokemonListAdapter
        pokemonRecyclerView.layoutManager =  GridLayoutManager(activity, 2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}