package com.rafaelfelipeac.rickandmortyapp.features.characterlist.data

import com.rafaelfelipeac.rickandmortyapp.core.network.RequestResult
import com.rafaelfelipeac.rickandmortyapp.core.network.handleRequest
import javax.inject.Inject
import com.rafaelfelipeac.rickandmortyapp.features.characterlist.data.model.CharacterListResponse

interface CharacterListRepository {
    suspend fun getCharacterList() : RequestResult<CharacterListResponse>
}

class CharacterListRepositoryImpl @Inject constructor(
    private val service: CharacterListService
) : CharacterListRepository {

    override suspend fun getCharacterList() : RequestResult<CharacterListResponse> = handleRequest { service.getCharacterList() }
}
