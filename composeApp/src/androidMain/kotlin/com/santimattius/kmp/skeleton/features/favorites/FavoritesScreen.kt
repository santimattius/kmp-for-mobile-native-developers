package com.santimattius.kmp.skeleton.features.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.santimattius.kmp.domain.Character
import com.santimattius.kmp.skeleton.R
import com.santimattius.kmp.skeleton.core.ui.AppBar
import com.santimattius.kmp.skeleton.core.ui.AppBarIconModel
import com.santimattius.kmp.skeleton.core.ui.Center

@Composable
fun FavoritesScreen(
    data: List<Character>,
    onClick: (Character) -> Unit = {},
    onFavoriteClick: (Character) -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.app_name),
                navIcon = AppBarIconModel(
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "goto back",
                    action = onBackClick
                )
            )
        }
    ) { padding ->
        if (data.isEmpty()) {
            Center {
                Text(
                    text = "There is no favorite content",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        } else {
            ListOfFavorites(
                characters = data,
                padding = padding,
                onClick = onClick,
                onFavoriteClick = onFavoriteClick
            )
        }
    }
}

@Composable
private fun ListOfFavorites(
    characters: List<Character>,
    padding: PaddingValues,
    onClick: (Character) -> Unit = {},
    onFavoriteClick: (Character) -> Unit = {}
) {
    LazyColumn(
        contentPadding = PaddingValues(dimensionResource(R.dimen.x_small)),
        modifier = Modifier.padding(padding)
    ) {
        items(characters, key = { it.id }) { character ->
            FavoriteRowItem(onClick, character, onFavoriteClick)
        }
    }
}

@Composable
private fun FavoriteRowItem(
    onClick: (Character) -> Unit,
    character: Character,
    onFavoriteClick: (Character) -> Unit
) {
    ListItem(
        modifier = Modifier.clickable { onClick(character) },
        leadingContent = {
            CircularAvatar(
                image = character.image,
                contentDescription = character.name,
                size = 60.dp
            )
        },
        headlineContent = { Text(text = character.name) },
        trailingContent = {
            IconButton(onClick = { onFavoriteClick(character) }) {
                Icon(
                    imageVector = if (character.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "",
                    tint = Color.Red
                )
            }
        }
    )
}

@Composable
private fun CircularAvatar(
    image: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .background(color = MaterialTheme.colorScheme.surface, shape = CircleShape)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = image,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(size),
        )
    }
}