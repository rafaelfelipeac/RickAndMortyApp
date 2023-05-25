package com.rafaelfelipeac.characterdetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelfelipeac.characterdetail.domain.CharacterDetailInteractor
import com.rafaelfelipeac.rickandmortyapp.core.network.RequestError
import com.rafaelfelipeac.rickandmortyapp.core.network.RequestException
import com.rafaelfelipeac.rickandmortyapp.core.network.RequestSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val interactor: CharacterDetailInteractor
) : ViewModel() {

    fun getCharacterDetail() {
        viewModelScope.launch {
            when (val response = interactor.getCharacterDetail()) {
                is RequestSuccess -> {
                    val x = ""
                }
                is RequestError -> {
                    val x = ""
                }
                is RequestException -> {
                    val x = ""
                }
            }
        }
    }
}