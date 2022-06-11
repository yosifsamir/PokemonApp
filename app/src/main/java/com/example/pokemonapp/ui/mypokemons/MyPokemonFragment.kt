package com.example.pokemonapp.ui.mypokemons

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.R
import com.example.pokemonapp.adapter.MyPokemonsRecyclerViewAdapter
import com.example.pokemonapp.data.PokemonDetails
import com.example.pokemonapp.databinding.FragmentMyPokemonBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyPokemonFragment : Fragment(R.layout.fragment_my_pokemon),
    MyPokemonsRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: MyPokemonViewModel by viewModels()

    private var _binding: FragmentMyPokemonBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MyPokemonsRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentMyPokemonBinding.bind(view)

        setupRecyclerView()



        viewModel.pokemonLiveData.observe(viewLifecycleOwner,
            Observer { savedPokemonList ->
                if (savedPokemonList!!.isEmpty()) {
                    binding.emptyRvMessage.visibility = View.VISIBLE
                } else {
                    binding.emptyRvMessage.visibility = View.GONE
                }
            })


        viewModel.getAllSavedPokemon()
    }

    private fun setupRecyclerView() {

        adapter = MyPokemonsRecyclerViewAdapter(this)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(activity)
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val pokemon = adapter.currentList[position]
                viewModel.deletePokemon(pokemon.id)
                Snackbar.make(view!!, "Pokemon deleted", Snackbar.LENGTH_LONG).show()

            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerView)
        }

        viewModel.pokemonLiveData.observe(viewLifecycleOwner,
            Observer { savedPokemonList -> adapter.submitList(savedPokemonList) })
    }

    override fun onItemClick(pokemon: PokemonDetails) {
        val action=MyPokemonFragmentDirections.actionMyPokemonsFragmentToPokemonDetails()
        action.pokemonId=pokemon.id
        action.isFetchFromRemote=false

        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}