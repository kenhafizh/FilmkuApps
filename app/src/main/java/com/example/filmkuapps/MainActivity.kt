package com.example.filmkuapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.filmkuapps.ui.common.theme.FilmkuAppsTheme
import com.example.filmkuapps.ui.nav.AppNavigationBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FilmkuAppsTheme {
                AppNavigationBar(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
