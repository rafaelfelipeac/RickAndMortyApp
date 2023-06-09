package com.rafaelfelipeac.rickandmortyapp.features.characterlist.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelfelipeac.rickandmortyapp.core.network.Request
import com.rafaelfelipeac.rickandmortyapp.features.characterlist.data.model.Character
import com.rafaelfelipeac.rickandmortyapp.features.characterlist.domain.CharacterListInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val interactor: CharacterListInteractor
) : ViewModel() {

    var characterList = mutableStateOf<List<Character>>(listOf())
    var loadError = mutableStateOf(EMPTY)
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)
    var isSearching = mutableStateOf(false)

    private var curPage = FIRST_PAGE
    private var cachedCharacterList = listOf<Character>()
    private var isSearchStarting = true

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            isLoading.value = true

            when (val result = interactor.getCharacterList(curPage)) {
                is Request.Success -> {
                    curPage++

                    endReached.value = curPage >= result.data.info.pages
                    loadError.value = EMPTY
                    isLoading.value = false
                    characterList.value += result.data.results
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

    fun searchCharacter(query: String) {
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

    companion object {
        const val FIRST_PAGE = 1
        const val EMPTY = ""
        const val GENERIC_ERROR_MESSAGE = "Something is wrong."
    }
}
