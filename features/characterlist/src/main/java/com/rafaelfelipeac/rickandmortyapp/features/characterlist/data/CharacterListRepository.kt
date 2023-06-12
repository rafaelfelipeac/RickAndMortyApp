package com.rafaelfelipeac.rickandmortyapp.features.characterlist.data

import com.rafaelfelipeac.rickandmortyapp.core.network.Request
import com.rafaelfelipeac.rickandmortyapp.core.network.handleRequest
import com.rafaelfelipeac.rickandmortyapp.features.characterlist.data.model.CharacterListResponse
import javax.inject.Inject

interface CharacterListRepository {
    suspend fun getCharacterList(page: Int): Request<CharacterListResponse>
}

class CharacterListRepositoryImpl @Inject constructor(
    private val service: CharacterListService
) : CharacterListRepository {

    override suspend fun getCharacterList(page: Int): Request<CharacterListResponse> =
        handleRequest { service.getCharacterList(page) }
}
