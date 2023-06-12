package com.rafaelfelipeac.rickandmortyapp.features.characterdetail.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelfelipeac.rickandmortyapp.core.network.Request
import com.rafaelfelipeac.rickandmortyapp.features.characterdetail.data.model.Character
import com.rafaelfelipeac.rickandmortyapp.features.characterdetail.domain.CharacterDetailInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val interactor: CharacterDetailInteractor
) : ViewModel() {

    val character = mutableStateOf<Character?>(null)
    var loadError = mutableStateOf(EMPTY)
    var isLoading = mutableStateOf(false)

    fun getCharacterDetail(characterId: Int) {
        isLoading.value = true

        viewModelScope.launch {
            when (val result = interactor.getCharacterDetail(characterId)) {
                is Request.Success -> {
                    loadError.value = EMPTY
                    isLoading.value = false
                    character.value = result.data
                }

                is Error -> {
                    loadError.value = result.message ?: EMPTY
                    isLoading.value = false
                }

                else -> {
                    loadError.value = GENERIC_ERROR_MESSAGE
                    isLoading.value = false
                }
            }
        }
    }

    companion object {
        const val EMPTY = ""
        const val GENERIC_ERROR_MESSAGE = "Something is wrong."
    }
}
