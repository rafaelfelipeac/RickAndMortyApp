package com.rafaelfelipeac.rickandmortyapp.features.characterdetail.data.model

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val location: CharacterLocation,
    val episode: List<String>
)
