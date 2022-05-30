package com.jordan.pictureapp.presentation.picture_favorite_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordan.pictureapp.domain.model.PictureItem
import com.jordan.pictureapp.domain.repository.PictureRepository
import com.jordan.pictureapp.presentation.common.ColumnState
import com.jordan.pictureapp.presentation.picture_detail_screen.PictureDetailState
import com.jordan.pictureapp.presentation.picture_screen.PictureEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PictureFavoriteViewModel @Inject constructor(
    private val repository: PictureRepository
): ViewModel() {

    var pictureState by mutableStateOf(PictureFavoriteState())

    var columnState by mutableStateOf(ColumnState())

    private var getPicturesJob: Job? = null

    init {
        getSavedPictures()
    }

    fun onEvent(event: PictureEvent) {
        when (event) {
            is PictureEvent.PictureColumns -> {
                if (columnState.columns == event.columns) {
                    return
                }
                columnState = columnState.copy(
                    columns = event.columns
                )
            }
            is PictureEvent.SpinnerOpen -> {
                columnState = columnState.copy(
                    isExpanded = true
                )
            }
            is PictureEvent.SpinnerClose -> {
                columnState = columnState.copy(
                    isExpanded = false
                )
            }
        }
    }

    private fun getSavedPictures() {
        getPicturesJob?.cancel()
        getPicturesJob = repository.getSavedPictureItems()
            .onEach { pictures ->
                pictureState = pictureState.copy(
                    pictures = pictures
                )
            }.launchIn(viewModelScope)
    }
}

data class PictureFavoriteState(
    val pictures: List<PictureItem> = emptyList()
)