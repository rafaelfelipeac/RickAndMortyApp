package com.rafaelfelipeac.rickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rafaelfelipeac.characterdetail.presentation.CharacterDetailScreen
import com.rafaelfelipeac.rickandmortyapp.features.characterlist.presentation.CharacterListScreen
import com.rafaelfelipeac.rickandmortyapp.core.theme.RickAndMortyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyAppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = CHARACTER_LIST_SCREEN
                ) {
                    composable(CHARACTER_LIST_SCREEN) {
                        CharacterListScreen()
                    }
                    composable(CHARACTER_DETAIL_SCREEN) {
                        CharacterDetailScreen()
                    }
                }
            }
        }
    }
}
