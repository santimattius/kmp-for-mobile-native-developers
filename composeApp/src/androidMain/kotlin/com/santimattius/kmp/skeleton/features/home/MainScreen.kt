package com.santimattius.kmp.skeleton.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.santimattius.kmp.domain.Character
import com.santimattius.kmp.skeleton.R
import com.santimattius.kmp.skeleton.core.theme.AppContainer
import com.santimattius.kmp.skeleton.core.ui.AppBar
import com.santimattius.kmp.skeleton.core.ui.AppBarIcon
import com.santimattius.kmp.skeleton.core.ui.AppBarIconModel

@Composable
fun HomeScreen(
    data: List<Character>,
    onClick: (Character) -> Unit = {},
    onFavorite: (Character) -> Unit = {},
    goToFavorite: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.app_name),
                actions = {
                    AppBarIcon(
                        navIcon = AppBarIconModel(
                            icon = Icons.Default.Favorite,
                            contentDescription = "Favorite",
                            action = goToFavorite
                        )
                    )
                }
            )
        }
    ) { padding ->
        GridOfCharacters(
            characters = data,
            padding = padding,
            onClick = onClick,
            onFavorite = onFavorite
        )
    }
}

@Composable
private fun GridOfCharacters(
    characters: List<Character>,
    padding: PaddingValues,
    onClick: (Character) -> Unit = {},
    onFavorite: (Character) -> Unit = {},
) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(dimensionResource(id = R.dimen.item_min_width)),
        contentPadding = PaddingValues(dimensionResource(R.dimen.x_small)),
        modifier = Modifier.padding(padding)
    ) {

        items(characters, key = { it.id }) { character ->
            CharacterItem(
                character = character,
                modifier = Modifier.clickable { onClick(character) },
                onClick = onClick,
                onFavorite = onFavorite
            )
        }
    }
}

@Composable
fun CharacterItem(
    character: Character,
    modifier: Modifier = Modifier,
    onClick: (Character) -> Unit = {},
    onFavorite: (Character) -> Unit = {}
) {
    Card(
        modifier = modifier
            .padding(dimensionResource(R.dimen.small))
            .clickable { onClick(character) }) {
        Box(contentAlignment = Alignment.BottomEnd) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .aspectRatio(ratio = 0.67f),
            )
            IconButton(onClick = { onFavorite(character) }) {
                Icon(
                    imageVector = if (character.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (character.isFavorite) Color.Red else Color.White
                )
            }
        }
    }
}

@Preview
@Composable
private fun ItemPreview() {
    AppContainer {
        CharacterItem(
            character = Character(
                id = 1,
                name = "name",
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                isFavorite = true
            )
        )
    }
}