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
            ALIVE -> StatusAlive
            DEAD -> StatusDead
            else -> StatusUndefined
        }
    }

    companion object {
        const val ALIVE = "Alive"
        const val DEAD = "Dead"
    }
}