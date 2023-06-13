package com.rafaelfelipeac.rickandmortyapp.features.characterdetail.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.rafaelfelipeac.characterdetail.R
import com.rafaelfelipeac.rickandmortyapp.core.extensions.getStatusColor
import com.rafaelfelipeac.rickandmortyapp.core.theme.ArrowSize
import com.rafaelfelipeac.rickandmortyapp.core.theme.CharacterDetailLoadingSize
import com.rafaelfelipeac.rickandmortyapp.core.theme.CharacterDetailSectionOffset
import com.rafaelfelipeac.rickandmortyapp.core.theme.CharacterStatusSize
import com.rafaelfelipeac.rickandmortyapp.core.theme.DarkGreen
import com.rafaelfelipeac.rickandmortyapp.core.theme.FontDetailLarge
import com.rafaelfelipeac.rickandmortyapp.core.theme.FontLarge
import com.rafaelfelipeac.rickandmortyapp.core.theme.FontMedium
import com.rafaelfelipeac.rickandmortyapp.core.theme.LightGreen
import com.rafaelfelipeac.rickandmortyapp.core.theme.PROGRESS_BAR_SCALE
import com.rafaelfelipeac.rickandmortyapp.core.theme.PaddingLarge
import com.rafaelfelipeac.rickandmortyapp.core.theme.PaddingMedium
import com.rafaelfelipeac.rickandmortyapp.core.theme.PaddingSmall
import com.rafaelfelipeac.rickandmortyapp.core.theme.RoundedCorner
import com.rafaelfelipeac.rickandmortyapp.core.theme.ShadowElevation
import com.rafaelfelipeac.rickandmortyapp.core.theme.SpacerMedium
import com.rafaelfelipeac.rickandmortyapp.core.theme.SpacerSmall
import com.rafaelfelipeac.rickandmortyapp.core.theme.Zero
import com.rafaelfelipeac.rickandmortyapp.features.characterdetail.data.model.Character
import java.util.Locale

const val MAX_LINES = 1
const val TOP_PADDING = 85
const val CHARACTER_IMAGE_SIZE = 250
const val OFFSET_DIVIDER = 2f
const val DETAIL_TOP_FRACTION = 0.2f

@Composable
fun CharacterDetailScreen(
    characterId: Int,
    navController: NavController,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    viewModel.getCharacterDetail(characterId)

    val character by remember { viewModel.character }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGreen)
            .padding(bottom = PaddingLarge)
    ) {
        CharacterDetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(DETAIL_TOP_FRACTION)
                .align(Alignment.TopCenter)
        )
        CharacterDetailStateWrapper(
            character = character,
            loadError = loadError,
            isLoading = isLoading,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = TOP_PADDING.dp + CHARACTER_IMAGE_SIZE.dp / OFFSET_DIVIDER,
                    start = PaddingLarge,
                    end = PaddingLarge,
                    bottom = PaddingLarge
                )
                .shadow(ShadowElevation, RoundedCornerShape(RoundedCorner))
                .clip(RoundedCornerShape(RoundedCorner))
                .background(MaterialTheme.colors.surface)
                .padding(PaddingLarge)
                .align(Alignment.BottomCenter),
            loadingModifier = Modifier
                .size(CharacterDetailLoadingSize)
                .align(Alignment.Center)
                .padding(
                    top = TOP_PADDING.dp + CHARACTER_IMAGE_SIZE.dp / OFFSET_DIVIDER,
                    start = PaddingLarge,
                    end = PaddingLarge,
                    bottom = PaddingLarge
                )
        )
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
        ) {
            CharacterImage(character)
        }
    }
}

@Composable
fun CharacterImage(character: Character?) {
    character?.image?.let {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(it)
                .crossfade(true)
                .build(),
            loading = {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.scale(PROGRESS_BAR_SCALE)
                )
            },
            contentDescription = character.name,
            modifier = Modifier
                .size(CHARACTER_IMAGE_SIZE.dp)
                .offset(y = TOP_PADDING.dp)
                .shadow(ShadowElevation, RoundedCornerShape(RoundedCorner))
                .clip(RoundedCornerShape(RoundedCorner))
        )
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
                .size(ArrowSize)
                .offset(SpacerMedium, SpacerMedium)
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
            .offset(y = CharacterDetailSectionOffset)
            .verticalScroll(scrollState)
    ) {
        CharacterDetailName(character)
        Spacer(modifier = Modifier.height(SpacerSmall))
        CharacterDetailStatus(character)
        Spacer(modifier = Modifier.height(SpacerSmall))
        CharacterDetailSpecies(character)
        Spacer(modifier = Modifier.height(SpacerSmall))
        CharacterDetailLastKnowLocation(character)
        Spacer(modifier = Modifier.height(SpacerSmall))
        CharacterDetailEpisodes(character)
    }
}

@Composable
private fun CharacterDetailName(character: Character) {
    Text(
        text = String.format(
            stringResource(R.string.character_detail_name_format),
            character.id,
            character.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        ),
        fontWeight = FontWeight.Bold,
        fontSize = FontDetailLarge,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.onSurface
    )
}

@Composable
private fun CharacterDetailStatus(character: Character) {
    Row {
        Box(
            modifier = Modifier
                .padding(PaddingMedium, PaddingSmall, Zero, Zero)
                .size(CharacterStatusSize)
                .clip(CircleShape)
                .background(character.status.getStatusColor())
        )
        Text(
            text = stringResource(R.string.character_detail_status),
            fontWeight = FontWeight.Bold,
            fontSize = FontMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PaddingMedium)
        )
    }
    Text(
        text = character.status,
        fontSize = FontLarge,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PaddingMedium),
        maxLines = MAX_LINES,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun CharacterDetailSpecies(character: Character) {
    Text(
        text = stringResource(R.string.character_detail_species),
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
        maxLines = MAX_LINES,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun CharacterDetailLastKnowLocation(character: Character) {
    Text(
        text = stringResource(R.string.character_detail_last_know_location),
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
        maxLines = MAX_LINES,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun CharacterDetailEpisodes(character: Character) {
    Text(
        text = stringResource(R.string.character_detail_episodes),
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
        maxLines = MAX_LINES,
        overflow = TextOverflow.Ellipsis
    )
}
