package com.example.pokemonapp.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemonapp.api.PokeApi
import com.example.pokemonapp.api.PokeApi.Companion.PAGING_STARTING_OFFSET_INDEX
import com.example.pokemonapp.data.Pokemon

class PokemonDataSource (private val pokeApi: PokeApi) : PagingSource<Int, Pokemon>() {
    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        var position = params.key

        if(position==null)
            position= PAGING_STARTING_OFFSET_INDEX

        return try {
            val response = pokeApi.getAllPokemon(
                params.loadSize,
                position
            )

            LoadResult.Page(
                data = response.results,
                prevKey = if (position == PAGING_STARTING_OFFSET_INDEX) null else position - params.loadSize,
                nextKey = if (response.results.isEmpty()) null else position + params.loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }


    }
}