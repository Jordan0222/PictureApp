package com.jordan.pictureapp.presentation.picture_detail_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordan.pictureapp.domain.model.PictureItem
import com.jordan.pictureapp.domain.repository.PictureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureDetailViewModel @Inject constructor(
    private val repository: PictureRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var detailState by mutableStateOf(PictureDetailState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        val hdurl = savedStateHandle.get<String>("hdurl").toString()
        val date = savedStateHandle.get<String>("date").toString()
        val description = savedStateHandle.get<String>("description").toString()
        val title = savedStateHandle.get<String>("title").toString()
        val copyright = savedStateHandle.get<String>("copyright").toString()
        val id = savedStateHandle.get<Int>("id")?.toInt()

        detailState = detailState.copy(
            hdurl = hdurl,
            date = date,
            description = description,
            title = title,
            copyright = copyright,
            id = id
        )
    }

    fun savePicture() {
        viewModelScope.launch {
            repository.insertPictureItem(
                PictureItem(
                    hdurl = detailState.hdurl,
                    date = detailState.date,
                    description = detailState.description,
                    title = detailState.title,
                    copyright = detailState.copyright
                )
            )
            _eventFlow.emit(
                UiEvent.ShowSnackbar(
                    message = "照片已儲存"
                )
            )
        }
    }

    fun deletePicture() {
        viewModelScope.launch {
            repository.deletePictureItem(
                PictureItem(
                    hdurl = detailState.hdurl,
                    date = detailState.date,
                    description = detailState.description,
                    title = detailState.title,
                    copyright = detailState.copyright
                )
            )
            _eventFlow.emit(
                UiEvent.ShowSnackbar(
                    message = "照片已刪除"
                )
            )
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
    }
}