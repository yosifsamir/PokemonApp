package com.example.pokemonapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon (val name: String,
                    val url: String) : Parcelable