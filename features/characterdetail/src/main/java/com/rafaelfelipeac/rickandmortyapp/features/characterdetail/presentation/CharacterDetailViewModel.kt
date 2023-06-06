package com.rafaelfelipeac.rickandmortyapp.features.characterdetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelfelipeac.rickandmortyapp.core.network.Error
import com.rafaelfelipeac.rickandmortyapp.core.network.Success
import com.rafaelfelipeac.rickandmortyapp.features.characterdetail.domain.CharacterDetailInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val interactor: CharacterDetailInteractor
) : ViewModel() {

    fun getCharacterDetail(characterId: Int) {
        viewModelScope.launch {
            when (val response = interactor.getCharacterDetail(characterId)) {
                is Success -> {
                    val x = ""
                }

                is Error -> {
                    val x = ""
                }

                else -> {
                    val x = ""
                    // Exception
                }
            }
        }
    }
}