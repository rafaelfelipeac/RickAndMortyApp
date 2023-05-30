package com.rafaelfelipeac.rickandmortyapp.features.characterlist.data

import com.rafaelfelipeac.rickandmortyapp.features.characterlist.data.model.CharacterListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterListService {

    @GET(CHARACTER_LIST_PATH)
    suspend fun getCharacterList(
        @Query("page") page: Int
    ): Response<CharacterListResponse>

    companion object {
        const val CHARACTER_LIST_PATH = "character"
    }
}