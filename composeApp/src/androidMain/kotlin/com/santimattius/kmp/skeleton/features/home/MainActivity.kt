package com.santimattius.kmp.skeleton.features.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santimattius.kmp.skeleton.core.theme.AppContainer
import com.santimattius.kmp.skeleton.features.favorites.FavoritesActivity
import com.santimattius.kmp.viewmodels.CharactersViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContainer {
                MainScreenRoute {
                    startActivity(Intent(this, FavoritesActivity::class.java))
                }
            }
        }
    }
}

@Composable
fun MainScreenRoute(
    viewModel: CharactersViewModel = koinViewModel(),
    goToFavorite: () -> Unit = {}
) {
    val data by viewModel.characters.collectAsStateWithLifecycle()
    HomeScreen(data = data, onFavorite = viewModel::addToFavorites, goToFavorite = goToFavorite)
}

@Preview
@Composable
fun AppAndroidPreview() {
    AppContainer {
        HomeScreen(data = emptyList())
    }
}