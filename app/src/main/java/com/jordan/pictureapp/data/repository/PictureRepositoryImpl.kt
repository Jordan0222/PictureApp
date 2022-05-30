package com.jordan.pictureapp.data.repository

import com.jordan.pictureapp.data.local.PictureDao
import com.jordan.pictureapp.data.local.PictureDatabase
import com.jordan.pictureapp.data.remote.PictureApi
import com.jordan.pictureapp.domain.model.PictureItem
import com.jordan.pictureapp.domain.repository.PictureRepository
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

class PictureRepositoryImpl(
    private val api: PictureApi,
    private val dao: PictureDao
): PictureRepository {
    override fun getSavedPictureItems(): Flow<List<PictureItem>> {
        return dao.getSavedPictureItem()
    }

    override suspend fun getPictureItems(page: Int, pageSize: Int): Result<List<PictureItem>> {
        return try {
            val response = api.getPictureItems()
            val pictureItems = response.map { it.toPictureItem() }
            val startingIndex = page * pageSize
            if (startingIndex + pageSize <= pictureItems.size) {
                Result.success(
                    pictureItems.slice(startingIndex until startingIndex + pageSize)
                )
            } else Result.success(emptyList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPictureSize(): Int {
        return try {
            val response = api.getPictureItems()
            val pictureItems = response.map { it.toPictureItem() }
            val size = pictureItems.size
            size
        } catch (e: Exception) {
            0
        }
    }

    override suspend fun insertPictureItem(pictureItem: PictureItem) {
        return dao.insertPictureItem(pictureItem)
    }

    override suspend fun deletePictureItem(pictureItem: PictureItem) {
        return dao.deletePictureItem(pictureItem)
    }

}