package com.example.filmkuapps.ui.features.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.filmkuapps.R
import com.example.filmkuapps.domain.model.MovieDetail
import com.example.filmkuapps.domain.model.Review
import com.example.filmkuapps.ui.common.theme.Poppins
import com.example.filmkuapps.ui.common.theme.PrimaryDark
import com.example.filmkuapps.ui.nav.AppScreen

@Composable
fun DetailScreen(
    navController: NavHostController,
    detailViewModel: DetailMoviewViewModel = viewModel(),
    reviewViewModel: ReviewMovieViewModel = viewModel()
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) } // Default to "Reviews" to match screenshot
    val tabs = listOf("About Movie", "Reviews", "Cast")
    val detailState by detailViewModel.detailMoviesState.collectAsState()
    val reviewState by reviewViewModel.reviewMovieState.collectAsState()

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
                    text = "Detail",
                    color = Color.White,
                    fontFamily = Poppins,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    Icons.Filled.Bookmark,
                    contentDescription = "Bookmark",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { innerPadding ->
        when (detailState) {
            is DetailMovieState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF0296E5))
                }
            }

            is DetailMovieState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error: ${(detailState as DetailMovieState.Error).message}",
                        color = Color.Red,
                        fontFamily = Poppins
                    )
                }
            }

            is DetailMovieState.Success -> {
                val movie = (detailState as DetailMovieState.Success).movies

                DetailMoviewContent(
                    movie = movie,
                    innerPadding = innerPadding,
                    tabs = tabs,
                    selectedTabIndex = selectedTabIndex,
                    onTabIndexChange = { selectedTabIndex = it },
                    navController = navController,
                    reviewState = reviewState
                )
            }


        }
    }
}

@Composable
fun DetailMoviewContent(
    movie: MovieDetail,
    innerPadding: androidx.compose.foundation.layout.PaddingValues,
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabIndexChange: (Int) -> Unit,
    navController: NavHostController,
    reviewState: ReviewMovieState
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Banner & Poster section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            // Banner
            AsyncImage(
                model = movie.backdropUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )

            // Rating Badge
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 100.dp, end = 16.dp)
                    .background(Color(0xAA252836), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Star,
                        null,
                        tint = Color(0xFFFFAD00),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = movie.rating.toString(),
                        color = Color(0xFFFFAD00),
                        fontFamily = Poppins,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Poster & Title Row
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 24.dp)
            ) {
                AsyncImage(
                    model = movie.posterUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .width(95.dp)
                        .height(120.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = movie.title,
                    color = Color.White,
                    fontFamily = Poppins,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(top = 16.dp)
                )
            }
        }

        // Info Row (Year | Duration | Genre)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MovieInfoItem(Icons.Default.CalendarToday, movie.releaseDate.take(4))
            InfoDivider()
            MovieInfoItem(Icons.Default.AccessTime, movie.duration)
            InfoDivider()
            MovieInfoItem(Icons.Default.ConfirmationNumber, movie.genres.firstOrNull() ?: "Unknown")
        }

        // Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            tabs.forEachIndexed { index, title ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                        .clickable { onTabIndexChange(index) }
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = if (selectedTabIndex == index) FontWeight.SemiBold else FontWeight.Normal
                    )
                    if (selectedTabIndex == index) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .background(Color(0xFF3A3F47), RoundedCornerShape(2.dp))
                        )
                    }
                }
            }
        }

        // Tab Content
        when (selectedTabIndex) { // Reviews Tab
            0 -> {
                Text(
                    text = movie.overview,
                    modifier = Modifier.padding(24.dp),
                    fontWeight = FontWeight.Light,
//                    color = Color.White,
                    fontSize = 12.sp,
                )

            }

            1 -> {
                when (reviewState) {
                    is ReviewMovieState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color(0xFF0296E5))
                        }

                    }

                    is ReviewMovieState.Success -> {
                        val reviews = (reviewState as ReviewMovieState.Success).movies
                        if (reviews.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No reviews yet",
                                    color = Color(0xFF929292),
                                    fontFamily = Poppins,
                                    fontSize = 14.sp
                                )
                            }

                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp)
                                    .padding(vertical = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                reviews.forEach{ review ->
                                    ReviewItem(
                                        name = review.authorDetails.username,
                                        rating = (review.authorDetails.rating ?: 0.0).toString(),
                                        review = review.content,
                                        profileImage = review.authorDetails.avatarPath?.let {
                                            "https://image.tmdb.org/t/p/w45$it"
                                        } ?: ""
                                    )
                                }
                            }
                        }
                    }

                    is ReviewMovieState.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Error: ${(reviewState as ReviewMovieState.Error).message}",
                                color = Color.Red,
                                fontFamily = Poppins,
                                fontSize = 14.sp
                            )
                        }
                    }

                }
            }

            2 -> {
                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                        .height(400.dp),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(65.dp),
                    userScrollEnabled = false
                ) {
                    items(12) { index ->
                        val filmId = (index + 1).toString()
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_cover),
                                contentDescription = "Movie Image",
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(123.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        navController.navigate(AppScreen.detailRoute(filmId))
                                    },
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "Tom Holland")
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun MovieInfoItem(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            icon,
            null,
            tint = Color(0xFF929292),
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = Color(0xFF929292),
            fontFamily = Poppins,
            fontSize = 12.sp
        )
    }
}

@Composable
fun InfoDivider() {
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .width(1.dp)
            .height(16.dp)
            .background(Color(0xFF929292))
    )
}

@Composable
fun ReviewItem(name: String, rating: String = "-", review: String, profileImage: String = "") {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (profileImage.isNotEmpty()) {
                AsyncImage(
                    model = profileImage,
                    contentDescription = "Author Avatar",
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF3A3F47)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = rating,
                color = Color(0xFF0296E5),
                fontFamily = Poppins,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = name,
                color = Color.White,
                fontFamily = Poppins,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = review,
                color = Color.White,
                fontFamily = Poppins,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }
    }
}
