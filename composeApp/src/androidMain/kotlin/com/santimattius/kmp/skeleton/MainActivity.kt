package com.santimattius.kmp.skeleton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santimattius.kmp.skeleton.theme.AppContainer
import com.santimattius.kmp.viewmodels.CharactersViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContainer {
                MainScreenRoute()
            }
        }
    }
}

@Composable
fun MainScreenRoute(
    viewModel: CharactersViewModel = koinViewModel()
) {
    val data by viewModel.characters.collectAsStateWithLifecycle()
    HomeScreen(data = data) {

    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    AppContainer {
        HomeScreen(data = emptyList()) {

        }
    }
}