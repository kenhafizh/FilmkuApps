package com.example.filmkuapps.ui.nav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.filmkuapps.ui.common.theme.PrimaryDark
import com.example.filmkuapps.ui.features.detail.DetailScreen
import com.example.filmkuapps.ui.features.home.HomeScreen
import com.example.filmkuapps.ui.features.search.SearchScreen
import com.example.filmkuapps.ui.features.watchlist.WatchlistScreen


@Composable
fun AppNavigationBar(
    modifier: Modifier = Modifier,
) {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if (currentDestination?.route?.startsWith("detail") != true) {
                NavigationBar(
                    containerColor = PrimaryDark,
                    contentColor = PrimaryDark
                ) {
                    items.forEach { screen ->
                        NavigationBarItem(
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF0296E5),
                                selectedTextColor = Color(0xFF0296E5),
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray,
                                indicatorColor = Color.Transparent,
                            ),
                            icon = {
                                Icon(
                                    screen.icon,
                                    contentDescription = screen.title,
                                )
                            },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(AppScreen.Home.route) {
                                        saveState = true
                                    }
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }

        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppScreen.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(AppScreen.Search.route) {
                SearchScreen()
            }
            composable(AppScreen.Watchlist.route) {
                WatchlistScreen(navController = navController)
            }
            composable(
                route = AppScreen.Detail.route,
                arguments = listOf(
                    navArgument("filmId") {
                        type = NavType.StringType
                    }
                )
            ) { navBackStackEntry ->
//                val filmId = navBackStackEntry.arguments?.getString("filmId") ?: "0"
                DetailScreen(navController = navController)
            }
        }
    }

}