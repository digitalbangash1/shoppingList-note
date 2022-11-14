package com.example.shoppinglist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector



sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object About : BottomBarScreen(
        route = "about",
        title = "About",
        icon = Icons.Default.Info
    )

    object MyList : BottomBarScreen(
        route = "myList",
        title = "MyList",
        icon = Icons.Default.List
    )

    object AddList : BottomBarScreen(
        route = "addList",
        title = "AddList",
        icon = Icons.Default.Add
    )
}
