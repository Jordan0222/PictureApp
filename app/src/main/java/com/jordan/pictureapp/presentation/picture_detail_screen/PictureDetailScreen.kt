package com.jordan.pictureapp.presentation.picture_detail_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.jordan.pictureapp.ui.theme.Blue20
import com.jordan.pictureapp.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PictureDetailScreen(
    navController: NavController,
    viewModel: PictureDetailViewModel = hiltViewModel()
) {
    val state = viewModel.detailState

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is PictureDetailViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (state.id == 2) viewModel.deletePicture() else viewModel.savePicture()
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = if (state.id == 2) Icons.Default.Delete else Icons.Default.Save,
                    contentDescription = "Save or Delete"
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
            ) {
                IconButton(
                    onClick = {
                        navController.navigateUp()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }

            Image(
                painter = rememberImagePainter(
                    data = state.hdurl
                ),
                contentDescription = "picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clickable {
                        navController.navigate(
                            Screen.PictureCheckScreen.route +
                                    "?hdurl=${state.hdurl}"
                        )
                    }
            )
            LazyColumn() {
                item {
                    Text(
                        text = state.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                item {
                    Text(
                        text = state.description,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                item {
                    Text(
                        text = state.copyright,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }

                item {
                    Text(
                        text = state.date,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}