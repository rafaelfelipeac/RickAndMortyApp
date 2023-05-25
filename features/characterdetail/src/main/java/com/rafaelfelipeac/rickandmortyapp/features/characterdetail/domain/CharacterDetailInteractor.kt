package com.rafaelfelipeac.rickandmortyapp.features.characterdetail.domain

import com.rafaelfelipeac.rickandmortyapp.features.characterdetail.data.CharacterDetailRepository
import com.rafaelfelipeac.rickandmortyapp.features.characterdetail.data.model.CharacterDetailResponse
import com.rafaelfelipeac.rickandmortyapp.core.network.RequestResult
import javax.inject.Inject

interface CharacterDetailInteractor {
    suspend fun getCharacterDetail(): RequestResult<CharacterDetailResponse>
}

class CharacterDetailInteractorImpl @Inject constructor(
    private val repository: CharacterDetailRepository
) : CharacterDetailInteractor {

    override suspend fun getCharacterDetail(): RequestResult<CharacterDetailResponse> =
        repository.getCharacterDetail()
}