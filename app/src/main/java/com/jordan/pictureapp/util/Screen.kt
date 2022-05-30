package com.jordan.pictureapp.util


sealed class Screen(val route: String) {
    object PictureScreen: Screen("picture_screen")
    object PictureFavoriteScreen: Screen("picture_favorite_screen")
    object PictureDetailScreen: Screen("picture_detail_screen")
    object PictureCheckScreen: Screen("picture_check_screen")
}