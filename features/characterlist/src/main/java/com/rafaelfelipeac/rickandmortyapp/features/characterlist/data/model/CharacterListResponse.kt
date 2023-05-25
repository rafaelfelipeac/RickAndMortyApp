package com.rafaelfelipeac.rickandmortyapp.features.characterlist.data.model

data class CharacterListResponse(
    val info: CharacterListInfo,
    val results: List<Character>,
)
