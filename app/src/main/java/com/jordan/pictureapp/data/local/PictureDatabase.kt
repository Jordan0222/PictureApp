package com.jordan.pictureapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jordan.pictureapp.domain.model.PictureItem

@Database(
    entities = [PictureItem::class],
    version = 1
)
abstract class PictureDatabase: RoomDatabase() {

    abstract val pictureDao: PictureDao

    companion object {
        const val DATABASE_NAME = "picture_db"
    }
}