package com.rafaelfelipeac.rickandmortyapp.features.characters.presentation

import androidx.lifecycle.ViewModel
import com.rafaelfelipeac.rickandmortyapp.features.characters.domain.CharactersInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val interactor: CharactersInteractor
) : ViewModel() {

    suspend fun getCharacters() {
        interactor.getCharacters()
    }
}