package com.rafaelfelipeac.rickandmortyapp.features.characters.data

import retrofit2.http.GET

interface CharactersService {

    @GET("/")
    suspend fun getCharacters()
}