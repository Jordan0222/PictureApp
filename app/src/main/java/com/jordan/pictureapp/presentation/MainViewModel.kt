package com.jordan.pictureapp.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordan.pictureapp.domain.repository.PictureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PictureRepository
): ViewModel() {

    private val _sizeState = mutableStateOf(SizeState())
    val sizeState: State<SizeState> = _sizeState

    private var getSavedPicturesJob: Job? = null

    init {
        getAllPictureSize()
        getSavedPictures()
    }

    private fun getAllPictureSize() {
        viewModelScope.launch {
            repository.getPictureSize()?.also {
                _sizeState.value = sizeState.value.copy(
                    allPictures = it
                )
            }
        }
    }

    private fun getSavedPictures() {
        getSavedPicturesJob?.cancel()
        getSavedPicturesJob = repository.getSavedPictureItems()
            .onEach { pictures ->
                _sizeState.value = sizeState.value.copy(
                    savedPictures = pictures.size
                )
            }.launchIn(viewModelScope)
    }
}

data class SizeState(
    val allPictures: Int = 0,
    val savedPictures: Int = 0
)