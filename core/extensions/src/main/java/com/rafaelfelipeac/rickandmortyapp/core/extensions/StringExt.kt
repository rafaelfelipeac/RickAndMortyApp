package com.rafaelfelipeac.rickandmortyapp.core.extensions

import androidx.compose.ui.graphics.Color
import com.rafaelfelipeac.rickandmortyapp.core.theme.StatusAlive
import com.rafaelfelipeac.rickandmortyapp.core.theme.StatusDead
import com.rafaelfelipeac.rickandmortyapp.core.theme.StatusUndefined

const val ALIVE = "Alive"
const val DEAD = "Dead"

fun String.getStatusColor(): Color {
    return when (this) {
        ALIVE -> StatusAlive
        DEAD -> StatusDead
        else -> StatusUndefined
    }
}
