package com.rafaelfelipeac.rickandmortyapp.features.characterlist.domain

import com.rafaelfelipeac.rickandmortyapp.features.characterlist.data.CharacterListRepository
import com.rafaelfelipeac.rickandmortyapp.features.characterlist.data.model.CharacterListResponse
import com.rafaelfelipeac.rickandmortyapp.network.RequestResult
import javax.inject.Inject

interface CharacterListInteractor {
    suspend fun getCharacterList() : RequestResult<CharacterListResponse>
}

class CharacterListInteractorImpl @Inject constructor(
    private val repository: CharacterListRepository
) : CharacterListInteractor{

    override suspend fun getCharacterList(): RequestResult<CharacterListResponse> = repository.getCharacterList()
}