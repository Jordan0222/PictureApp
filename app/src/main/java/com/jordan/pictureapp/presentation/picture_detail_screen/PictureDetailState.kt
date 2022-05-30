package com.jordan.pictureapp.presentation.picture_detail_screen

data class PictureDetailState(
    val hdurl: String = "",
    val date: String = "",
    val description: String = "",
    val title: String = "",
    val copyright: String = "",
    val id: Int? = -1,
)
