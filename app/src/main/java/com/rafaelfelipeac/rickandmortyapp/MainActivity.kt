package com.rafaelfelipeac.rickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rafaelfelipeac.rickandmortyapp.core.navigation.CHARACTER_DETAIL_ID
import com.rafaelfelipeac.rickandmortyapp.core.navigation.CHARACTER_DETAIL_ROUTE
import com.rafaelfelipeac.rickandmortyapp.core.navigation.CHARACTER_LIST_SCREEN
import com.rafaelfelipeac.rickandmortyapp.core.theme.RickAndMortyAppTheme
import com.rafaelfelipeac.rickandmortyapp.features.characterdetail.presentation.CharacterDetailScreen
import com.rafaelfelipeac.rickandmortyapp.features.characterlist.presentation.CharacterListScreen
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
                        CharacterListScreen(navController = navController)
                    }
                    composable(
                        route = CHARACTER_DETAIL_ROUTE,
                        arguments = listOf(
                            navArgument(CHARACTER_DETAIL_ID) {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        val characterId = remember {
                            it.arguments?.getInt(CHARACTER_DETAIL_ID) ?: DEFAULT_CHARACTER_ID
                        }

                        CharacterDetailScreen(
                            characterId = characterId,
                            navController = navController
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val DEFAULT_CHARACTER_ID = 1
    }
}
