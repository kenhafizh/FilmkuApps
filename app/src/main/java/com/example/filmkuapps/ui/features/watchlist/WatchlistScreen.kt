package com.example.filmkuapps.ui.features.watchlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.filmkuapps.R
import com.example.filmkuapps.ui.common.theme.Poppins
import com.example.filmkuapps.ui.common.theme.PrimaryDark
import com.example.filmkuapps.ui.features.search.MovieModel
import com.example.filmkuapps.ui.features.search.SearchMovieWidget

@Composable
fun WatchlistScreen(navController: NavController) {
    val movieList = listOf(
        MovieModel("Sang Pemimpi", "9.5", "2022", "120m", "Drama", R.drawable.img_cover),
        MovieModel("Laskar Pelangi", "9.8", "2008", "124m", "Adventure", R.drawable.img_cover),
        MovieModel("Interstellar", "9.0", "2014", "169m", "Sci-Fi", R.drawable.img_cover),
        MovieModel("The Dark Knight", "9.1", "2008", "152m", "Action", R.drawable.img_cover),
        MovieModel("The Dark Knight", "9.1", "2008", "152m", "Action", R.drawable.img_cover),
        MovieModel("The Dark Knight", "9.1", "2008", "152m", "Action", R.drawable.img_cover),
    )
    Scaffold(
        containerColor = PrimaryDark,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.clickable { navController.popBackStack() },
                    tint = Color.White
                )
                Text(
                    text = "Watch list",
                    color = Color.White,
                    fontFamily = Poppins,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(24.dp))
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(
                    horizontal = 24.dp,
                ),
        ) {
            items(movieList) { movie ->
                SearchMovieWidget(movie = movie)
            }
        }
    }
}


