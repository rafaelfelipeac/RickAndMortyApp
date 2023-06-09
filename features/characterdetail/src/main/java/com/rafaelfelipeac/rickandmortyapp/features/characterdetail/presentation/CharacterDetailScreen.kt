package com.rafaelfelipeac.rickandmortyapp.features.characterdetail.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.rafaelfelipeac.rickandmortyapp.core.theme.DarkGreen
import com.rafaelfelipeac.rickandmortyapp.core.theme.FontLarge
import com.rafaelfelipeac.rickandmortyapp.core.theme.FontMedium
import com.rafaelfelipeac.rickandmortyapp.core.theme.LightGreen
import com.rafaelfelipeac.rickandmortyapp.core.theme.PaddingMedium
import com.rafaelfelipeac.rickandmortyapp.core.theme.SpacerSmall
import com.rafaelfelipeac.rickandmortyapp.features.characterdetail.data.model.Character
import java.util.Locale

@Composable
fun CharacterDetailScreen(
    characterId: Int,
    navController: NavController,
    topPadding: Dp = 85.dp,
    characterImageSize: Dp = 250.dp,
    viewModel: CharacterDetailViewModel = hiltViewModel(),
) {
    viewModel.getCharacterDetail(characterId)

    val character by remember { viewModel.character }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGreen)
            .padding(bottom = 16.dp)
    ) {
        CharacterDetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter)
        )
        CharacterDetailStateWrapper(
            character = character,
            loadError = loadError,
            isLoading = isLoading,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = topPadding + characterImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .padding(
                    top = topPadding + characterImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
        )
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
        ) {
            character?.image?.let {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it)
                        .crossfade(true)
                        .build(),
                    loading = {
                        CircularProgressIndicator(
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.scale(0.5f)
                        )
                    },
                    contentDescription = character?.name,
                    modifier = Modifier
                        .size(characterImageSize)
                        .offset(y = topPadding)
                )
            }
        }
    }
}

@Composable
fun CharacterDetailTopSection(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        DarkGreen,
                        Color.Transparent
                    )
                )
            )
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}

@Composable
fun CharacterDetailStateWrapper(
    character: Character?,
    loadError: String,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    when {
        character != null -> {
            CharacterDetailSection(
                character = character,
                modifier = modifier
            )
        }

        loadError.isNotEmpty() -> {
            Text(
                text = loadError,
                color = Color.Red,
                modifier = modifier
            )
        }

        isLoading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = loadingModifier
            )
        }
    }
}

@Composable
fun CharacterDetailSection(
    character: Character,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 120.dp)
            .verticalScroll(scrollState) // problem
    ) {
        Text(
            text = "${character.id} - ${character.name.capitalize(Locale.ROOT)}",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(SpacerSmall))
        Text(
            text = "Status:",
            fontWeight = FontWeight.Bold,
            fontSize = FontMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PaddingMedium)
        )
        Text(
            text = character.status,
            fontSize = FontLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PaddingMedium),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(SpacerSmall))
        Text(
            text = "Species:",
            fontWeight = FontWeight.Bold,
            fontSize = FontMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PaddingMedium)
        )
        Text(
            text = character.species,
            fontSize = FontLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PaddingMedium),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(SpacerSmall))
        Text(
            text = "Last know location:",
            fontWeight = FontWeight.Bold,
            fontSize = FontMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PaddingMedium)
        )
        Text(
            text = character.location.name,
            fontSize = FontLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PaddingMedium),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(SpacerSmall))
        Text(
            text = "Episodes:",
            fontWeight = FontWeight.Bold,
            fontSize = FontMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PaddingMedium)
        )
        Text(
            text = character.episode.size.toString(),
            fontSize = FontLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PaddingMedium),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}