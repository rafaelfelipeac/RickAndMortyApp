package com.rafaelfelipeac.rickandmortyapp.features.characterdetail.domain

import com.rafaelfelipeac.rickandmortyapp.core.network.Request
import com.rafaelfelipeac.rickandmortyapp.features.characterdetail.data.CharacterDetailRepository
import com.rafaelfelipeac.rickandmortyapp.features.characterdetail.data.model.Character
import javax.inject.Inject

interface CharacterDetailInteractor {
    suspend fun getCharacterDetail(characterId: Int): Request<Character>
}

class CharacterDetailInteractorImpl @Inject constructor(
    private val repository: CharacterDetailRepository
) : CharacterDetailInteractor {

    override suspend fun getCharacterDetail(characterId: Int): Request<Character> =
        repository.getCharacterDetail(characterId)
}