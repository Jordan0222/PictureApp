package com.jordan.pictureapp.presentation.picture_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordan.pictureapp.data.paging.DefaultPaginator
import com.jordan.pictureapp.domain.repository.PictureRepository
import com.jordan.pictureapp.presentation.common.ColumnState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureViewModel @Inject constructor(
    private val repository: PictureRepository
): ViewModel() {

    var state by mutableStateOf(PictureScreenState())

    var columnState by mutableStateOf(ColumnState())

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            repository.getPictureItems(nextPage, 10)
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            state = state.copy(error = it?.localizedMessage)
        },
        onSuccess = { items, newKey ->
            state = state.copy(
                items = state.items + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    init {
        loadNextItems()
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItem()
        }
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
}