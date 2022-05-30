package com.jordan.pictureapp.presentation.picture_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.jordan.pictureapp.util.Screen
import kotlinx.coroutines.launch


@Composable
fun PictureScreen(
    navController: NavController,
    viewModel: PictureViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val columnState = viewModel.columnState
    val columnList = listOf(1, 2, 3, 4, 5)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "每列照片數量",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Row(
                modifier = Modifier
                    .clickable {
                        viewModel.onEvent(PictureEvent.SpinnerOpen)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = columnState.columns.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription ="Spinner"
                )
                DropdownMenu(
                    expanded = columnState.isExpanded,
                    onDismissRequest = {
                        viewModel.onEvent(PictureEvent.SpinnerClose)
                    }
                ) {
                    columnList.forEach { columns ->
                        DropdownMenuItem(
                            onClick = {
                                viewModel.onEvent(PictureEvent.SpinnerClose)
                                viewModel.onEvent(PictureEvent.PictureColumns(columns))
                            }
                        ) {
                            Text(
                                text = columns.toString(),
                                style = MaterialTheme.typography.h5
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(columnState.columns)
        ) {
            items(state.items.size) { i ->
                val pictureItem = state.items[i]
                if (i >= state.items.size - 1 && !state.endReached && !state.isLoading) {
                    viewModel.loadNextItems()
                }
                Box(
                    modifier = Modifier.clickable {
                        navController.navigate(
                            route = Screen.PictureDetailScreen.route +
                                    "?copyright=${pictureItem.copyright}" +
                                    "&date=${pictureItem.date}" +
                                    "&description=${pictureItem.description}" +
                                    "&hdurl=${pictureItem.hdurl}" +
                                    "&title=${pictureItem.title}" +
                                    "&id=${1}"
                        )
                    }
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = pictureItem.hdurl
                        ),
                        contentDescription = "picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .border(
                                width = 1.dp,
                                color = Color.White
                            )
                    )
                }
            }
            item {
                if (state.isLoading) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}