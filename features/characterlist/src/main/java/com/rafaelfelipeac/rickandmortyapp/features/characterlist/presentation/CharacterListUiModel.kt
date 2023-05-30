package com.rafaelfelipeac.rickandmortyapp.features.characterlist.presentation

import androidx.compose.ui.graphics.Color
import com.rafaelfelipeac.rickandmortyapp.core.theme.StatusAlive
import com.rafaelfelipeac.rickandmortyapp.core.theme.StatusDead
import com.rafaelfelipeac.rickandmortyapp.core.theme.StatusUndefined
import com.rafaelfelipeac.rickandmortyapp.features.characterlist.data.model.Character

interface CharacterListUiModel {
    fun getStatusColor(character: Character): Color
}

class CharacterListUiModelImpl : CharacterListUiModel {

    override fun getStatusColor(character: Character): Color {
        return when (character.status) {
            "Alive" -> StatusAlive
            "Dead" -> StatusDead
            else -> StatusUndefined
        }
    }
}