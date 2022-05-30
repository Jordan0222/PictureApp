package com.jordan.pictureapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jordan.pictureapp.presentation.common.BottomNavItem
import com.jordan.pictureapp.presentation.common.BottomNavigationBar
import com.jordan.pictureapp.presentation.picture_check_screen.PictureCheckScreen
import com.jordan.pictureapp.presentation.picture_detail_screen.PictureDetailScreen
import com.jordan.pictureapp.presentation.picture_favorite_screen.PictureFavoriteScreen
import com.jordan.pictureapp.presentation.picture_screen.PictureScreen
import com.jordan.pictureapp.ui.theme.PictureAppTheme
import com.jordan.pictureapp.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PictureAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BottomBarNavigation()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomBarNavigation(
    viewModel: MainViewModel = hiltViewModel()
) {
    val bottomBarState = rememberSaveable { mutableStateOf(true) }
    val navController = rememberNavController()
    val state = viewModel.sizeState

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        name = "${state.value.allPictures} 張",
                        route = Screen.PictureScreen.route,
                        icon = Icons.Default.Home
                    ),
                    BottomNavItem(
                        name = "${state.value.savedPictures} 張",
                        route = Screen.PictureFavoriteScreen.route,
                        icon = Icons.Default.Favorite
                    )
                ),
                navController = navController,
                bottomBarState = bottomBarState
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.PictureScreen.route
        ) {
            composable(
                route = Screen.PictureScreen.route
            ) {
                bottomBarState.value = true
                PictureScreen(navController = navController)
            }

            composable(
                route = Screen.PictureFavoriteScreen.route
            ) {
                bottomBarState.value = true
                PictureFavoriteScreen(navController = navController)
            }

            composable(
                route = Screen.PictureDetailScreen.route +
                        "?copyright={copyright}&date={date}&description={description}&hdurl={hdurl}&title={title}&id={id}",
                arguments = listOf(
                    navArgument("copyright") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument("date") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument("description") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument("hdurl") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument("title") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument("id") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ) {
                bottomBarState.value = false
                PictureDetailScreen(navController)
            }

            composable(
                route = Screen.PictureCheckScreen.route +
                        "?hdurl={hdurl}"
            ) {
                bottomBarState.value = false
                PictureCheckScreen(navController = navController)
            }
        }
    }
}