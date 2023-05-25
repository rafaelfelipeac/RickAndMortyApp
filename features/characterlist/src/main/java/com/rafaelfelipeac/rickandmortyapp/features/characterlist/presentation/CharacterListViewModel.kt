package com.rafaelfelipeac.rickandmortyapp.features.characterlist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelfelipeac.rickandmortyapp.features.characterlist.domain.CharacterListInteractor
import com.rafaelfelipeac.rickandmortyapp.network.RequestError
import com.rafaelfelipeac.rickandmortyapp.network.RequestException
import com.rafaelfelipeac.rickandmortyapp.network.RequestSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val interactor: CharacterListInteractor
) : ViewModel() {

    fun getCharacterList() {
        viewModelScope.launch {
            when (val response = interactor.getCharacterList()) {
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