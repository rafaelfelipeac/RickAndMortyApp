package com.rafaelfelipeac.rickandmortyapp.features.characterlist.data

import com.rafaelfelipeac.rickandmortyapp.features.characterlist.data.model.CharacterListResponse
import retrofit2.Response
import retrofit2.http.GET

interface CharacterListService {

    @GET("character")
    suspend fun getCharacterList() : Response<CharacterListResponse>
}