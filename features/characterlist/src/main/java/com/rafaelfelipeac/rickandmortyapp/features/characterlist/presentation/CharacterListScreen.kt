package com.rafaelfelipeac.rickandmortyapp.features.characterlist.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.rafaelfelipeac.rickandmortyapp.core.navigation.CHARACTER_DETAIL_NAV
import com.rafaelfelipeac.rickandmortyapp.core.theme.CharacterImageHeight
import com.rafaelfelipeac.rickandmortyapp.core.theme.CharacterImageOffset
import com.rafaelfelipeac.rickandmortyapp.core.theme.CharacterStatusSize
import com.rafaelfelipeac.rickandmortyapp.core.theme.FontLarge
import com.rafaelfelipeac.rickandmortyapp.core.theme.FontMedium
import com.rafaelfelipeac.rickandmortyapp.core.theme.FontSmall
import com.rafaelfelipeac.rickandmortyapp.core.theme.PaddingLarge
import com.rafaelfelipeac.rickandmortyapp.core.theme.PaddingMedium
import com.rafaelfelipeac.rickandmortyapp.core.theme.PaddingSmall
import com.rafaelfelipeac.rickandmortyapp.core.theme.RoundedCorner
import com.rafaelfelipeac.rickandmortyapp.core.theme.ShadowElevation
import com.rafaelfelipeac.rickandmortyapp.core.theme.SpacerLarge
import com.rafaelfelipeac.rickandmortyapp.core.theme.SpacerMedium
import com.rafaelfelipeac.rickandmortyapp.core.theme.SpacerSmall
import com.rafaelfelipeac.rickandmortyapp.core.theme.TextPaddingHorizontal
import com.rafaelfelipeac.rickandmortyapp.core.theme.TextPaddingVertical
import com.rafaelfelipeac.rickandmortyapp.core.theme.Zero
import com.rafaelfelipeac.rickandmortyapp.features.characterlist.R
import com.rafaelfelipeac.rickandmortyapp.features.characterlist.data.model.Character

const val EMPTY = ""
const val MAX_LINES = 1
const val WEIGHT_DEFAULT = 1f
const val PROGRESS_BAR_SCALE = 0.5f
const val MAX_ELEMENTS_PER_ROW = 2
const val MIN_ELEMENTS_PER_ROW = 1
const val EVEN_CHECK = 2
const val EVEN_RESULT = 0
const val ITEM_OFFSET = 1

@Composable
fun CharacterListScreen(
    navController: NavController,
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(SpacerLarge))
            Image(
                painter = painterResource(R.drawable.rick_and_morty),
                contentDescription = stringResource(R.string.logo_content_description),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(SpacerSmall))
            SearchBar(
                hint = stringResource(R.string.search_hint),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingLarge)
            ) {
                viewModel.searchCharacter(it)
            }
            Spacer(modifier = Modifier.height(SpacerSmall))
            CharacterList(navController = navController)
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = EMPTY,
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf(EMPTY)
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != EMPTY)
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = MAX_LINES,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(ShadowElevation, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = TextPaddingHorizontal, vertical = TextPaddingVertical)
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused
                }
        )
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = TextPaddingHorizontal, vertical = TextPaddingVertical)
            )
        }
    }
}

@Composable
fun CharacterList(
    navController: NavController,
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    val characterList by remember { viewModel.characterList }
    val endReached by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    val isSearching by remember { viewModel.isSearching }

    LazyColumn(contentPadding = PaddingValues(PaddingLarge)) {
        val itemCount = if (characterList.size % EVEN_CHECK == EVEN_RESULT) {
            characterList.size / MAX_ELEMENTS_PER_ROW
        } else {
            characterList.size / MAX_ELEMENTS_PER_ROW + MIN_ELEMENTS_PER_ROW
        }

        items(itemCount) {
            if (it >= itemCount - ITEM_OFFSET && !endReached && !isLoading && !isSearching) {
                viewModel.loadCharacters()
            }
            CharacterRow(rowIndex = it, entries = characterList, navController = navController)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if (loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.loadCharacters()
            }
        }
    }

}

@Composable
fun CharacterEntry(
    entry: Character,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(ShadowElevation, RoundedCornerShape(RoundedCorner))
            .clip(RoundedCornerShape(RoundedCorner))
            .background(MaterialTheme.colors.surface)
            .clickable {
                navController.navigate(String.format(CHARACTER_DETAIL_NAV, entry.id))
            }
    ) {
        Column {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(entry.image)
                    .crossfade(true)
                    .build(),
                loading = {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.scale(PROGRESS_BAR_SCALE)
                    )
                },
                contentDescription = entry.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CharacterImageHeight)
                    .offset(y = CharacterImageOffset)
            )
            Text(
                text = entry.name,
                fontWeight = FontWeight.Bold,
                fontSize = FontLarge,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = PaddingMedium),
                maxLines = MAX_LINES,
                overflow = TextOverflow.Ellipsis
            )
            Row {
                Box(
                    modifier = Modifier
                        .padding(PaddingMedium, PaddingSmall, Zero, Zero)
                        .size(CharacterStatusSize)
                        .clip(CircleShape)
                        .background(viewModel.getStatusColor(entry))
                )
                Text(
                    text = String.format(
                        stringResource(R.string.status_format),
                        entry.status,
                        entry.species
                    ),
                    fontSize = FontMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = PaddingMedium),
                    maxLines = MAX_LINES,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(SpacerSmall))
            Text(
                text = stringResource(R.string.last_know_location),
                fontWeight = FontWeight.Bold,
                fontSize = FontSmall,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = PaddingMedium)
            )
            Text(
                text = entry.location.name,
                fontSize = FontMedium,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = PaddingMedium),
                maxLines = MAX_LINES,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(SpacerMedium))
        }
    }
}

@Composable
fun CharacterRow(
    rowIndex: Int,
    entries: List<Character>,
    navController: NavController
) {
    Column {
        Row {
            CharacterEntry(
                entry = entries[rowIndex * MAX_ELEMENTS_PER_ROW],
                navController = navController,
                modifier = Modifier.weight(WEIGHT_DEFAULT)
            )
            Spacer(modifier = Modifier.width(SpacerMedium))
            if (entries.size >= rowIndex * MAX_ELEMENTS_PER_ROW + MAX_ELEMENTS_PER_ROW) {
                CharacterEntry(
                    entry = entries[rowIndex * MAX_ELEMENTS_PER_ROW + MIN_ELEMENTS_PER_ROW],
                    navController = navController,
                    modifier = Modifier.weight(WEIGHT_DEFAULT)
                )
            } else {
                Spacer(modifier = Modifier.weight(WEIGHT_DEFAULT))
            }
        }
        Spacer(modifier = Modifier.height(SpacerMedium))
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = FontLarge)
        Spacer(modifier = Modifier.height(SpacerSmall))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(R.string.retry))
        }
    }
}