package com.jordan.pictureapp.presentation.picture_check_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PictureCheckViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var hdurlState by mutableStateOf("")

    init {
        val hdurl = savedStateHandle.get<String>("hdurl").toString()
        hdurlState = hdurl
    }
}