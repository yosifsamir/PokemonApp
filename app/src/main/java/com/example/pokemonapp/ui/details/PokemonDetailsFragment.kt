package com.example.pokemonapp.ui.details


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.pokemonapp.R
import com.example.pokemonapp.data.PokemonDetails
import com.example.pokemonapp.databinding.FragmentPokemonDatailsBinding
import com.example.pokemonapp.utils.getImageUrlFromId
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailsFragment : Fragment(R.layout.fragment_pokemon_datails) {

    private val args by navArgs<PokemonDetailsFragmentArgs>()


    private var _binding: FragmentPokemonDatailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PokemonDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        _binding = FragmentPokemonDatailsBinding.bind(view)
        val pokemon=args.pokemon
        val pokemonId=args.pokemonId
        val isFetchFromRemote=args.isFetchFromRemote

        viewModel.pokemon=pokemon
        viewModel.pokemonId=pokemonId
        viewModel.isFetchFromRemote=isFetchFromRemote


        viewModel.pokemonLiveData.observe(viewLifecycleOwner,
            Observer { pokemonDetails -> populateScreenData(pokemonDetails!!) })

        viewModel.fetchData()

        setupBackButton()

        setupSavePokemonButton()

    }

    private fun populateScreenData(data: PokemonDetails) {

        //Separating data
        val imageUrl = getImageUrlFromId(data.id)
        val pokemonIdString = "#${data.id}"
        val capitalizedName = data.name.capitalize() // to capitalize fisrt character
        val pokemonWeight = (data.weight.toFloat() / 10).toString() + "Kg"
        val pokemonHeight = (data.height.toFloat() / 10).toString() + "M"

        binding.apply {

            Glide.with(this@PokemonDetailsFragment)
                .load(imageUrl)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_pokeball_black)
                .into(pokemonImageImg)

            pokemonIdTxt.text = pokemonIdString
            pokemonNameDetailsTxt.text = capitalizedName
            weightValueTxt.text = pokemonWeight
            heightValueTxt.text = pokemonHeight

            setupStatViews(data)

        }

    }


    private fun setupSavePokemonButton() {
        binding.savePokemonBtn.setOnClickListener {
            viewModel.pokemonLiveData.value?.let { pokemon ->
                viewModel.savePokemon(pokemon)
                Toast.makeText(context, "${pokemon.name} saved!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupBackButton() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupStatViews(data: PokemonDetails) {

        val hpValue = data.stats[0].base_stat
        val atkValue = data.stats[1].base_stat
        val defValue = data.stats[2].base_stat
        val spdValue = data.stats[5].base_stat

        binding.apply {
            hpTxt.text= hpValue.toString()
            atkTxt.text= atkValue.toString()
            defTxt.text= defValue.toString()
            spdTxt.text= spdValue.toString()
        }

    }

}