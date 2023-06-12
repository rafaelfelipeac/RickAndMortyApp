package com.rafaelfelipeac.rickandmortyapp.features.characterdetail.data

import com.rafaelfelipeac.rickandmortyapp.features.characterdetail.data.model.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterDetailService {

    @GET(CHARACTER_DETAIL_PATH)
    suspend fun getCharacterDetail(
        @Path(CHARACTER_ID) characterId: Int
    ): Response<Character>

    companion object {
        const val CHARACTER_ID = "characterId"
        const val CHARACTER_DETAIL_PATH = "character/{$CHARACTER_ID}"
    }
}
