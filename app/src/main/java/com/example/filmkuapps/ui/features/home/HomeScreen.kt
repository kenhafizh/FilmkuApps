package com.example.filmkuapps.ui.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridCells.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.filmkuapps.ui.common.components.shimmerEffect
import com.example.filmkuapps.ui.common.theme.Poppins
import com.example.filmkuapps.ui.common.theme.PrimaryDark
import com.example.filmkuapps.ui.nav.AppScreen

@Composable
fun HomeScreen(navController: NavController, viewModel: MovieViewModel = viewModel()) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Now Playing", "Upcoming", "Top Rated")

    val popularMoviesState by viewModel.popularMoviesState.collectAsStateWithLifecycle()
    val nowPlayingMoviesState by viewModel.nowPlayingState.collectAsStateWithLifecycle()
    val upcomingMoviesState by viewModel.upcomingMoviesState.collectAsStateWithLifecycle()
    val topRatedMoviesState by viewModel.topRatedMoviesState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = PrimaryDark,
    ) { innerPadding ->
        Column(
        ) {
            Text(
                text = "What do you want to watch?",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
            )

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
                        Icons.Default.Search,
                        "search",
                        tint = Color(0xFF929292)
                    )
                },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                placeholder = {
                    Text(text = "Search")
                }
            )

            // Most Popular Movies List Horizontal
            when (val state = popularMoviesState) {
                is MovieUiState.Loading -> {
                    // Tampilkan Loading Indicator
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(top = 20.dp, start = 12.dp),
                        userScrollEnabled = false // Matikan scroll saat sedang loading
                    ) {
                        items(5) { // Buat 5 dummy item
                            Box(
                                modifier = Modifier
                                    .padding(bottom = 30.dp, start = 10.dp)
                                    .width(144.dp)
                                    .height(210.dp)
                                    .shimmerEffect() // <-- Panggil modifier di sini!
                            )
                        }
                    }
                }
                is MovieUiState.Success -> {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(top = 20.dp, start = 12.dp)
                    ) {
                        // Gunakan itemsIndexed untuk mendapatkan index dan data movie
                        // Ambil 10 data teratas saja (opsional) menggunakan .take(10)

                        itemsIndexed(state.movies.take(5)) { index, movie ->
                            val number = (index + 1).toString()
                            val filmId = movie.id.toString()

                            Box(
                                modifier = Modifier
                                    .padding(bottom = 30.dp, start = 10.dp)
                                    .clickable {
                                        navController.navigate(AppScreen.detailRoute(filmId))
                                    }
                            ) {
                                AsyncImage(
                                    model = movie.posterUrl,
                                    contentDescription = movie.title,
                                    modifier = Modifier
                                        .width(144.dp)
                                        .height(210.dp)
                                        .clip(RoundedCornerShape(16.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                // Fill Layer (Primary Dark)
                                Text(
                                    text = number,
                                    fontSize = 96.sp,
                                    color = Color(0xFF8e9094),
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .offset(x = (-10).dp, y = 50.dp)
                                )
                            }
                        }


                    }
                }
                is MovieUiState.Error -> {
                    // Tampilkan pesan error
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = state.message, color = Color.Red)
                    }
                }

                is DetailMovieState.Error -> TODO()
                DetailMovieState.Loading -> TODO()
                is DetailMovieState.Success -> TODO()
            }




            // TAB
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.padding(top = 24.dp),
                containerColor = Color.Transparent,
                contentColor = Color.White,
                edgePadding = 16.dp,
                divider = {},
                indicator = { tabPositions ->
                    if (selectedTabIndex < tabPositions.size) {
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = Color(0xFF3A3F47)
                        )
                    }
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    val isSelected = selectedTabIndex == index
                    Tab(
                        selected = isSelected,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                fontFamily = Poppins,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Light,
                                fontSize = 14.sp
                            )
                        }
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> {
                    when (val state = nowPlayingMoviesState) {
                        is MovieUiState.Loading -> {
                            LazyVerticalGrid (
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .padding(top = 16.dp),
                                columns = Fixed(3),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                            ) {
                                items(5) { // Buat 5 dummy item
                                    Box(
                                        modifier = Modifier
                                            .padding(bottom = 30.dp, start = 10.dp)
                                            .width(100.dp)
                                            .height(145.dp)
                                            .shimmerEffect() // <-- Panggil modifier di sini!
                                    )
                                }
                            }
                        }
                        is MovieUiState.Success -> {
                            LazyVerticalGrid(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .padding(top = 16.dp),
                                columns = Fixed(3),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                            ) {
                                itemsIndexed(state.movies.take(10)) { index, movie ->
                                    val number = (index + 1).toString()
                                    val filmId = movie.id.toString()

                                    AsyncImage(
                                        model = movie.posterUrl,
                                        contentDescription = movie.title,
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(145.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .clickable {
                                                navController.navigate(AppScreen.detailRoute(filmId))
                                            },
                                        contentScale = ContentScale.Crop
                                    )
                                }



                            }
                        }
                        is MovieUiState.Error -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = state.message, color = Color.Red)
                            }
                        }

                        is DetailMovieState.Error -> TODO()
                        DetailMovieState.Loading -> TODO()
                        is DetailMovieState.Success -> TODO()
                    }

                }
                1 -> {
                    when (val state = upcomingMoviesState) {
                        is MovieUiState.Loading -> {
                            LazyVerticalGrid (
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .padding(top = 16.dp),
                                columns = Fixed(3),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                            ) {
                                items(5) { // Buat 5 dummy item
                                    Box(
                                        modifier = Modifier
                                            .padding(bottom = 30.dp, start = 10.dp)
                                            .width(100.dp)
                                            .height(145.dp)
                                            .shimmerEffect() // <-- Panggil modifier di sini!
                                    )
                                }
                            }
                        }
                        is MovieUiState.Success -> {
                            LazyVerticalGrid(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .padding(top = 16.dp),
                                columns = Fixed(3),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                            ) {
                                itemsIndexed(state.movies.take(10)) { index, movie ->
                                    val filmId = movie.id.toString()

                                    AsyncImage(
                                        model = movie.posterUrl,
                                        contentDescription = movie.title,
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(145.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .clickable {
                                                navController.navigate(AppScreen.detailRoute(filmId))
                                            },
                                        contentScale = ContentScale.Crop
                                    )
                                }



                            }
                        }
                        is MovieUiState.Error -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = state.message, color = Color.Red)
                            }
                        }

                        is DetailMovieState.Error -> TODO()
                        DetailMovieState.Loading -> TODO()
                        is DetailMovieState.Success -> TODO()
                    }

                }
                2 -> {
                    when (val state = topRatedMoviesState) {
                        is MovieUiState.Loading -> {
                            LazyVerticalGrid (
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .padding(top = 16.dp),
                                columns = Fixed(3),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                            ) {
                                items(5) { // Buat 5 dummy item
                                    Box(
                                        modifier = Modifier
                                            .padding(bottom = 30.dp, start = 10.dp)
                                            .width(100.dp)
                                            .height(145.dp)
                                            .shimmerEffect() // <-- Panggil modifier di sini!
                                    )
                                }
                            }
                        }
                        is MovieUiState.Success -> {
                            LazyVerticalGrid(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .padding(top = 16.dp),
                                columns = Fixed(3),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                            ) {
                                itemsIndexed(state.movies.take(10)) { index, movie ->
                                    val filmId = movie.id.toString()

                                    AsyncImage(
                                        model = movie.posterUrl,
                                        contentDescription = movie.title,
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(145.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .clickable {
                                                navController.navigate(AppScreen.detailRoute(filmId))
                                            },
                                        contentScale = ContentScale.Crop
                                    )
                                }



                            }
                        }
                        is MovieUiState.Error -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = state.message, color = Color.Red)
                            }
                        }

                        is DetailMovieState.Error -> TODO()
                        DetailMovieState.Loading -> TODO()
                        is DetailMovieState.Success -> TODO()
                    }

                }
                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tabs[selectedTabIndex],
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }


        }
    }
}
