package com.rafaelfelipeac.rickandmortyapp.features.characterlist.presentation

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.rafaelfelipeac.rickandmortyapp.core.network.Success
import com.rafaelfelipeac.rickandmortyapp.features.characterlist.domain.CharacterListInteractor
import com.rafaelfelipeac.rickandmortyapp.features.characterlist.data.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val interactor: CharacterListInteractor
) : ViewModel() {

    private var curPage = 1

    var characterList = mutableStateOf<List<Character>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    private var cachedCharacterList = listOf<Character>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)

    init {
        loadCharacterPaginated()
    }

    fun searchCharacterList(query: String) {
        val listToSearch = if (isSearchStarting) {
            characterList.value
        } else {
            cachedCharacterList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                characterList.value = cachedCharacterList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.name.contains(query.trim(), ignoreCase = true)
            }
            if (isSearchStarting) {
                cachedCharacterList = characterList.value
                isSearchStarting = false
            }
            characterList.value = results
            isSearching.value = true
        }
    }

    fun loadCharacterPaginated() {
        viewModelScope.launch {
            isLoading.value = true

            when (val result = interactor.getCharacterList(curPage)) {
                is Success -> {
                    curPage++

                    endReached.value = curPage >= result.data.info.pages
                    loadError.value = ""
                    isLoading.value = false
                    characterList.value += result.data.results
                }

                is Error -> {
                    loadError.value = result.message ?: ""
                    isLoading.value = false
                }

                else -> {
                    // exception
                }
            }
        }
    }
}