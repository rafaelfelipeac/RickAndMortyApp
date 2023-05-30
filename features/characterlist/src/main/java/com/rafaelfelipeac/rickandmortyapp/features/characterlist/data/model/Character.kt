package com.rafaelfelipeac.rickandmortyapp.features.characterlist.data.model

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val location: CharacterLocation
)
