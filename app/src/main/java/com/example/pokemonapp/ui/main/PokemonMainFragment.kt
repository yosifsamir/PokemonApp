package com.example.pokemonapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemonapp.R
import com.example.pokemonapp.adapter.PokemonPagingAdapter
import com.example.pokemonapp.data.Pokemon
import com.example.pokemonapp.databinding.FragmentPokemonMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonMainFragment : Fragment(R.layout.fragment_pokemon_main),
    PokemonPagingAdapter.OnItemClickListener {

    private val viewModel: PokemonMainViewModel by viewModels()

    private var _binding: FragmentPokemonMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PokemonPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPokemonMainBinding.bind(view)

        setupRecyclerView()


    }

    private fun setupRecyclerView() {

        adapter = PokemonPagingAdapter(this)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(context, 3)
        }

        viewModel.pokemonListLive.observe(viewLifecycleOwner,
            Observer { pagingData -> adapter.submitData(viewLifecycleOwner.lifecycle, pagingData!!) })
    }

    override fun onItemClick(pokemon: Pokemon) {
        val action = PokemonMainFragmentDirections.actionPokemonMainToPokemonDetails()
        action.pokemon=pokemon
        action.isFetchFromRemote=true

        findNavController().navigate(action)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}