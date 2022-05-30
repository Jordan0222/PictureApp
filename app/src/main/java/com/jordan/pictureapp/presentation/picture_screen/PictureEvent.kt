package com.jordan.pictureapp.presentation.picture_screen

sealed class PictureEvent {
    data class PictureColumns(val columns: Int): PictureEvent()
    object SpinnerClose: PictureEvent()
    object SpinnerOpen: PictureEvent()
}
