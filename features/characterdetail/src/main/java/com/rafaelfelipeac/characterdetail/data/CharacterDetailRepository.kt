package com.rafaelfelipeac.characterdetail.data

import com.rafaelfelipeac.characterdetail.data.model.CharacterDetailResponse
import com.rafaelfelipeac.rickandmortyapp.network.RequestResult
import com.rafaelfelipeac.rickandmortyapp.network.handleRequest
import javax.inject.Inject

interface CharacterDetailRepository {
    suspend fun getCharacterDetail(): RequestResult<CharacterDetailResponse>
}

class CharacterDetailRepositoryImpl @Inject constructor(
    private val service: CharacterDetailService
) : CharacterDetailRepository {

    override suspend fun getCharacterDetail(): RequestResult<CharacterDetailResponse> =
        handleRequest { service.getCharacterDetail() }
}
