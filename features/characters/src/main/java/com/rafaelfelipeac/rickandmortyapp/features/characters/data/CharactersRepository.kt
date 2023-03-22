package com.rafaelfelipeac.rickandmortyapp.features.characters.data

import javax.inject.Inject

interface CharactersRepository {
    suspend fun getCharacters()
}

class CharactersRepositoryImpl @Inject constructor(
    private val service: CharactersService
) : CharactersRepository {

    override suspend fun getCharacters() {
        return service.getCharacters()
    }
}