package com.jordan.pictureapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.jordan.pictureapp.domain.model.PictureItem
import kotlinx.coroutines.flow.Flow

@Dao
interface PictureDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertPictureItem(pictureItem: PictureItem)

    @Query("SELECT * FROM pictureItems")
    fun getSavedPictureItem(): Flow<List<PictureItem>>

    @Delete
    suspend fun deletePictureItem(pictureItem: PictureItem)
}