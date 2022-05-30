package com.jordan.pictureapp.domain.repository

import com.jordan.pictureapp.domain.model.PictureItem
import kotlinx.coroutines.flow.Flow

interface PictureRepository {

    fun getSavedPictureItems(): Flow<List<PictureItem>>

    suspend fun getPictureItems(page: Int, pageSize: Int): Result<List<PictureItem>>

    suspend fun getPictureSize(): Int?

    suspend fun insertPictureItem(pictureItem: PictureItem)

    suspend fun deletePictureItem(pictureItem: PictureItem)
}