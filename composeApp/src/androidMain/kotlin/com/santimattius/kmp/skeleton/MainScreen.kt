package com.santimattius.kmp.skeleton

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.santimattius.kmp.domain.Character
import com.santimattius.kmp.skeleton.ui.AppBar

@Composable
fun HomeScreen(
    data: List<Character>,
    onClick: (Character) -> Unit
) {
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.app_name)
            )
        }
    ) { padding ->
        GridOfCharacters(data, padding, onClick)
    }
}

@Composable
private fun GridOfCharacters(
    characters: List<Character>,
    padding: PaddingValues,
    onClick: (Character) -> Unit,
) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(dimensionResource(id = R.dimen.item_min_width)),
        contentPadding = PaddingValues(dimensionResource(R.dimen.x_small)),
        modifier = Modifier.padding(padding)
    ) {

        items(characters) { character ->
            CharacterItem(
                character = character,
                modifier = Modifier.clickable { onClick(character) }
            )
        }
    }
}

@Composable
fun CharacterItem(character: Character, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(dimensionResource(R.dimen.small))) {
        AsyncImage(
            model = character.image,
            contentDescription = character.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .aspectRatio(ratio = 0.67f),
        )
    }
}