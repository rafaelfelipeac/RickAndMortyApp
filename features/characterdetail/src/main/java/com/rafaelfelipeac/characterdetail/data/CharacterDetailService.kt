package com.rafaelfelipeac.characterdetail.data

import com.rafaelfelipeac.characterdetail.data.model.CharacterDetailResponse
import retrofit2.Response
import retrofit2.http.GET

interface CharacterDetailService {

    @GET("character-detail")
    suspend fun getCharacterDetail() : Response<CharacterDetailResponse>
}