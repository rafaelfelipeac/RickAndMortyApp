package com.rafaelfelipeac.rickandmortyapp.features.characters.domain

import com.rafaelfelipeac.rickandmortyapp.features.characters.data.CharactersRepository
import javax.inject.Inject

interface CharactersInteractor {
    suspend fun getCharacters()
}

class CharactersInteractorImpl @Inject constructor(
    private val repository: CharactersRepository
) : CharactersInteractor{

    override suspend fun getCharacters() {
        return repository.getCharacters()
    }
}