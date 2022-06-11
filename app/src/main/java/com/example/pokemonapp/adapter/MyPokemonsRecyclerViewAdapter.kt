package com.example.pokemonapp.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

import com.example.pokemonapp.R
import com.example.pokemonapp.data.PokemonDetails
import com.example.pokemonapp.data.Type
import com.example.pokemonapp.databinding.SavedPokemonItemBinding

class MyPokemonsRecyclerViewAdapter (private val listener: OnItemClickListener):
    ListAdapter<PokemonDetails, MyPokemonsRecyclerViewAdapter.PokemonViewHolder>(POKEMON_COMPARATOR) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = SavedPokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    inner class PokemonViewHolder(private val binding: SavedPokemonItemBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                binding.root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val pokemon = getItem(position)
                        if (pokemon != null) {
                            listener.onItemClick(pokemon)
                        }
                    }
                }
            }
        }

        fun bind(pokemon: PokemonDetails) {
            val idText = "#${pokemon.id}"
            val nameText = pokemon.name.capitalize()
            val imageUrl =
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png"

            binding.apply {

                setupTypes(pokemon.types)

                Glide.with(itemView)
                    .load(imageUrl)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_pokeball_black)
                    .into(pokemonSavedImg)

                pokemonSavedNameTxt.text = nameText
                pokemonIdSavedTxt.text = idText
            }
        }

        private fun setupTypes(
            types: List<Type>
        ) {
            when (types.size) {
                1 -> {
                    binding.apply {
                        typeSlotOneSavedTxt.apply {
                            visibility = View.VISIBLE
                            text = types[0].type.name
                        }
                    }
                }
                2 -> {
                    binding.apply {
                        typeSlotOneSavedTxt.apply {
                            visibility = View.VISIBLE
                            text = types[0].type.name
                        }
                        typeSlotTwoSavedTxt.apply {
                            visibility = View.VISIBLE
                            text = types[1].type.name
                        }

                    }
                }
            }

        }

    }

    interface OnItemClickListener {
        fun onItemClick(pokemon: PokemonDetails)
    }

    companion object {
        private val POKEMON_COMPARATOR = object : DiffUtil.ItemCallback<PokemonDetails>() {
            override fun areItemsTheSame(oldItem: PokemonDetails, newItem: PokemonDetails) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PokemonDetails, newItem: PokemonDetails) =
                oldItem == newItem
        }
    }
}