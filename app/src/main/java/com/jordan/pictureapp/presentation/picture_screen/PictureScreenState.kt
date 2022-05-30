package com.jordan.pictureapp.presentation.picture_screen

import com.jordan.pictureapp.domain.model.PictureItem

data class PictureScreenState(
    val isLoading: Boolean = false,
    val items: List<PictureItem> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0
)
