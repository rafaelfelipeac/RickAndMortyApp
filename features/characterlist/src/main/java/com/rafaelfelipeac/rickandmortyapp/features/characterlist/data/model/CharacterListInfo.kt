package com.rafaelfelipeac.rickandmortyapp.features.characterlist.data.model

data class CharacterListInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
