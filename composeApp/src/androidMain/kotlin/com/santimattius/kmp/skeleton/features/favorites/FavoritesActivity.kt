package com.santimattius.kmp.skeleton.features.favorites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santimattius.kmp.skeleton.core.theme.AppContainer
import com.santimattius.kmp.skeleton.features.home.HomeScreen
import com.santimattius.kmp.viewmodels.FavoritesViewModel
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.koinViewModel

class FavoritesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContainer {
                FavoritesScreenRoute(onBack = ::onBackPressed)
            }
        }
    }
}

@Suppress("ParamsComparedByRef")
@Composable
fun FavoritesScreenRoute(
    viewModel: FavoritesViewModel = koinViewModel(),
    onBack: () -> Unit = {}
) {
    val data by viewModel.characters.collectAsStateWithLifecycle()
    FavoritesScreen(data = data, onFavoriteClick = viewModel::addToFavorites, onBackClick = onBack)
}

@Preview
@Composable
fun FavoritesPreview() {
    AppContainer {
        HomeScreen(data = persistentListOf())
    }
}