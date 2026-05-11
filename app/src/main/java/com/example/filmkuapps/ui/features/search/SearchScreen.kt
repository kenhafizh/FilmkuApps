package com.example.filmkuapps.ui.features.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.LocalActivity
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.filmkuapps.R
import com.example.filmkuapps.ui.common.theme.PrimaryDark

@Composable
fun SearchScreen() {
    var searchQuery by remember { mutableStateOf("") }

    val movieList = listOf(
        MovieModel("Sang Pemimpi", "9.5", "2022", "120m", "Drama", R.drawable.img_cover),
        MovieModel("Laskar Pelangi", "9.8", "2008", "124m", "Adventure", R.drawable.img_cover),
        MovieModel("Interstellar", "9.0", "2014", "169m", "Sci-Fi", R.drawable.img_cover),
        MovieModel("The Dark Knight", "9.1", "2008", "152m", "Action", R.drawable.img_cover)
    )

    Scaffold(
        containerColor = PrimaryDark
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
            Row(modifier = Modifier.padding(vertical = 32.dp)) {
                Icon(
                    Icons.AutoMirrored.Default.KeyboardArrowLeft, "search", tint = Color(0xFF929292)
                )
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                Text(text = "Search Movie")
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    Icons.Default.Info,
                    "Search Info",

                    )
            }

            // Search Bar
            TextField(
                value = searchQuery,
                singleLine = true,
                onValueChange = { searchQuery = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF67686D),
                    unfocusedContainerColor = Color(0xFF67686D),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White,
                ),
                trailingIcon = {
                    Icon(
                        Icons.Default.Search, "search", tint = Color(0xFF929292)
                    )
                },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Search")
                })

            // Search Item List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(movieList) { movie ->
                    SearchMovieWidget(movie = movie)
                }
            }
        }
    }
}

@Composable
fun SearchMovieWidget(movie: MovieModel) {
    Row(modifier = Modifier.padding(top = 24.dp)) {
        Image(
            painter = painterResource(id = R.drawable.img_cover),
            contentDescription = "Movie Image",
            modifier = Modifier
                .width(100.dp)
                .height(140.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = movie.title,
                modifier = Modifier.padding(bottom = 16.dp),
                fontWeight = FontWeight.W500,
                fontSize = 16.sp
            )
            MovieSearchItem(
                Icons.Outlined.StarRate ,
                contentDescription = "Rating",
                text = movie.rating,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFFFA500)
            )
            MovieSearchItem(
                Icons.Outlined.DateRange,
                contentDescription = "Release Date",
                text = movie.year,
                color = Color.White
            )
            MovieSearchItem(
                Icons.Outlined.AccessTime,
                contentDescription = "Duration",
                text = movie.duration,
                color = Color.White
            )
            MovieSearchItem(
                Icons.Outlined.LocalActivity,
                contentDescription = "Genre",
                text = movie.genre,
                color = Color.White
            )
        }
    }
}

@Composable
fun MovieSearchItem (icon: ImageVector, contentDescription: String, text: String, color: Color, fontWeight: FontWeight = FontWeight.Normal) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            icon,
            contentDescription = contentDescription,
            tint = color,
            modifier = Modifier
                .padding(end = 4.dp)
                .width(16.dp)
                .height(16.dp)
        )
        Text(text = text, fontSize = 12.sp, color = color, fontWeight = fontWeight)
    }
}

