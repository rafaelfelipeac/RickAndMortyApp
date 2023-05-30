package com.rafaelfelipeac.rickandmortyapp.features.characterdetail.domain

import com.rafaelfelipeac.rickandmortyapp.features.characterdetail.data.CharacterDetailRepository
import com.rafaelfelipeac.rickandmortyapp.features.characterdetail.data.model.CharacterDetailResponse
import com.rafaelfelipeac.rickandmortyapp.core.network.Request
import javax.inject.Inject

interface CharacterDetailInteractor {
    suspend fun getCharacterDetail(): Request<CharacterDetailResponse>
}

class CharacterDetailInteractorImpl @Inject constructor(
    private val repository: CharacterDetailRepository
) : CharacterDetailInteractor {

    override suspend fun getCharacterDetail(): Request<CharacterDetailResponse> =
        repository.getCharacterDetail()
}