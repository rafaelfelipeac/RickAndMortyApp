package com.rafaelfelipeac.rickandmortyapp.features.characterlist.data

import javax.inject.Inject
import com.rafaelfelipeac.rickandmortyapp.features.characterlist.data.model.CharacterListResponse
import com.rafaelfelipeac.rickandmortyapp.network.*

interface CharacterListRepository {
    suspend fun getCharacterList() : RequestResult<CharacterListResponse>
}

class CharacterListRepositoryImpl @Inject constructor(
    private val service: CharacterListService
) : CharacterListRepository {

    override suspend fun getCharacterList() : RequestResult<CharacterListResponse> = handleRequest { service.getCharacterList() }
}
