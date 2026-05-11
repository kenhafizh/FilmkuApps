package com.example.filmkuapps.ui.features.home

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.filmkuapps.R
import com.example.filmkuapps.ui.common.theme.Poppins
import com.example.filmkuapps.ui.common.theme.PrimaryDark
import com.example.filmkuapps.ui.nav.AppScreen

@Composable
fun HomeScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Now Playing", "Upcoming", "Top Rated", "Popular")

    Scaffold(
        containerColor = PrimaryDark,
    ) { _ ->
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
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 20.dp, start = 12.dp)
            ) {
                items(5) { index ->
                    val number = (index + 1).toString()
                    val filmId = (index + 1).toString()
                    Box(
                        modifier = Modifier
                            .padding(bottom = 30.dp, start = 10.dp)
                            .clickable {
                            navController.navigate(AppScreen.detailRoute(filmId))
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_cover),
                            contentDescription = "Movie Image",
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
                    LazyVerticalGrid(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp),
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(6) { index ->
                            val  filmId = (index + 1).toString()
                            Image(
                                painter = painterResource(id = R.drawable.img_cover),
                                contentDescription = "Movie Image",
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
