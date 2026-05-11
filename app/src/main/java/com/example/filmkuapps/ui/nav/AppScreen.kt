package com.example.filmkuapps.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppScreen (val route: String, val title: String, val icon: ImageVector){
    object  Home : AppScreen("home", "Home", Icons.Default.Home)
    object  Search : AppScreen("search", "Search", Icons.Default.Search)
    object  Watchlist : AppScreen("watchlist", "Watch List", Icons.Default.Favorite)
    object Detail: AppScreen("detail/{filmId}", title = "Detail Movie", Icons.Default.Home)

    companion object {
        fun detailRoute(filmId: String) = "detail/$filmId"
    }
}

val items = listOf(
    AppScreen.Home,
    AppScreen.Search,
    AppScreen.Watchlist,
)