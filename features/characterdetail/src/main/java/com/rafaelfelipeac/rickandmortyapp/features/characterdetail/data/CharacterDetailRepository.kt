package com.rafaelfelipeac.rickandmortyapp.features.characterdetail.data

import com.rafaelfelipeac.rickandmortyapp.core.network.Request
import com.rafaelfelipeac.rickandmortyapp.core.network.handleRequest
import com.rafaelfelipeac.rickandmortyapp.features.characterdetail.data.model.Character
import javax.inject.Inject

interface CharacterDetailRepository {
    suspend fun getCharacterDetail(characterId: Int): Request<Character>
}

class CharacterDetailRepositoryImpl @Inject constructor(
    private val service: CharacterDetailService
) : CharacterDetailRepository {

    override suspend fun getCharacterDetail(characterId: Int): Request<Character> =
        handleRequest { service.getCharacterDetail(characterId) }
}
