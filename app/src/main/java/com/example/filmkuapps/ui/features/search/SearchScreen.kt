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
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.filmkuapps.R
import com.example.filmkuapps.domain.model.MovieSearch
import com.example.filmkuapps.ui.common.theme.PrimaryDark
import com.example.filmkuapps.ui.utils.formatDate
import kotlin.toString

@Composable
fun SearchScreen(searchViewModel: SearchViewModel = viewModel()) {
    var searchQuery by remember { mutableStateOf("") }
    val results by searchViewModel.results.collectAsState()
    val isLoading by searchViewModel.isLoading.collectAsState()
    val error by searchViewModel.error.collectAsState()

    var localQuery by remember { mutableStateOf("") }


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
                value = localQuery,
                singleLine = true,
                onValueChange = { q ->
                    localQuery = q
                    searchViewModel.setQuery(q)
                },
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
                if (isLoading) {
                    item {

                    }
                }
                if (results.isEmpty() && localQuery.isNotBlank() && !isLoading) {
                    item {
                        Text("No results", color = Color.Gray, modifier = Modifier.padding(16.dp))
                    }
                } else {
                    items(results) { movieSearch ->
                        val movieModel = MovieModel(
                            title = movieSearch.title,
                            rating = movieSearch.voteAverage.toString(),
                            year = movieSearch.releaseDate.takeIf { it.isNotEmpty() } ?: "-",
                            duration = "-", // not available in search results
                            genre = if (movieSearch.genreIds.isNotEmpty()) movieSearch.genreIds.first().toString() else "-",
                            imageRes = R.drawable.img_cover
                        )
                        SearchMovieWidget(movie = movieSearch)
                    }
                }

            }
        }
    }
}

@Composable
fun SearchMovieWidget(movie: MovieSearch) {
    Row(modifier = Modifier.padding(top = 24.dp)) {
        AsyncImage(
            model = movie.posterPath?.let { "https://image.tmdb.org/t/p/w185$it" } ?: R.drawable.img_cover,
            contentDescription = movie.title,
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
            Text(
                text = movie.overview,
                modifier = Modifier.padding(bottom = 8.dp),
                fontWeight = FontWeight.Normal,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp
            )
            MovieSearchItem(
                Icons.Outlined.DateRange,
                contentDescription = "Release Date",
                text = movie.releaseDate.formatDate(outputFormat = "EEEE, d MMMM yyyy"),
                color = Color.White
            )
        }
    }
}

@Composable
fun MovieSearchItem(
    icon: ImageVector,
    contentDescription: String,
    text: String,
    color: Color,
    fontWeight: FontWeight = FontWeight.Normal
) {
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

