package com.dreyesyho.myapplication.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dreyesyho.myapplication.navigation.Screens

@Composable
fun WeathersNavigationDrawer(
    onFavoriteClicked: () -> Unit,
    onHomeClicked: () -> Unit,
    onDrawerClicked:  (() -> Unit)? = null
) {
    val items = listOf(Screens.WeathersScreen, Screens.FavoriteWeathersScreen)
    val selectedItem = remember { mutableStateOf(items[0]) }
    Column(
        modifier = Modifier
            .wrapContentWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Weather",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            // Show IconButton only if onDrawerClicked is provided
            if (onDrawerClicked != null) {
                IconButton(
                    onClick = onDrawerClicked
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Navigation Drawer Icon"
                    )
                }
            }
        }
        NavigationDrawerItem(
            label = { Text(text = "Weathers") },
            selected = selectedItem.value == Screens.WeathersScreen,
            onClick = {
                onHomeClicked()
                selectedItem.value = Screens.WeathersScreen
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home Icon"
                )
            }
        )

        NavigationDrawerItem(
            label = { Text(text = "Favorites") },
            selected = selectedItem.value == Screens.FavoriteWeathersScreen,
            onClick = {
                onFavoriteClicked()
                selectedItem.value = Screens.FavoriteWeathersScreen
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite Icon"
                )
            }
        )

    }
}