package com.santimattius.kmp.skeleton.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppBar(
    title: String = "",
    navIcon: AppBarIconModel? = null,
    actions: @Composable RowScope.() -> Unit = {},
    containerColor: Color = MaterialTheme.colorScheme.primary,
    titleContentColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    val navigationIcon = createNavIcon(navIcon)
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = navigationIcon,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = containerColor,
            titleContentColor = titleContentColor,
            navigationIconContentColor = titleContentColor,
            actionIconContentColor = titleContentColor,
        ),
        actions = actions
    )
}

@Composable
private fun createNavIcon(
    navIcon: AppBarIconModel?,
): @Composable () -> Unit {
    val navigationIcon: @Composable () -> Unit = if (navIcon == null) {
        {}
    } else {
        {
            AppBarIcon(navIcon = navIcon)
        }
    }
    return navigationIcon
}

@Composable
internal fun AppBarIcon(navIcon: AppBarIconModel) {
    IconButton(onClick = { navIcon.action() }) {
        Icon(imageVector = navIcon.icon, contentDescription = navIcon.contentDescription)
    }
}

internal data class AppBarIconModel(
    val icon: ImageVector,
    val contentDescription: String,
    val action: () -> Unit,
)