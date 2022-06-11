package com.example.pokemonapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.pokemonapp.R
import com.example.pokemonapp.data.Pokemon
import com.example.pokemonapp.databinding.PokemonLayoutBinding
import com.example.pokemonapp.utils.getImageUrlFromUrl

class PokemonPagingAdapter (private val listener:OnItemClickListener) :
    PagingDataAdapter<Pokemon,PokemonPagingAdapter.PokemonViewHolder>(POKEMON_COMPARATOR)  {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding=PokemonLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PokemonViewHolder(binding)
    }
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }





    inner class PokemonViewHolder (private val binding:PokemonLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
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

        fun bind(pokemon: Pokemon){
            //Gets image url from pokemon url
            val drawableUrl = getImageUrlFromUrl(pokemon.url)
            binding.apply {
                Glide.with(itemView)
                    .load(drawableUrl)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_pokeball_black)
                    .into(pokemonImg)

                pokemonNameTxt.text = pokemon.name.capitalize()
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(pokemon: Pokemon)
    }

    companion object {
        private val POKEMON_COMPARATOR = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon) =
                oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon) =
                oldItem == newItem
        }
    }
}