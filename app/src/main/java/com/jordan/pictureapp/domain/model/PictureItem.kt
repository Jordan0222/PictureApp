package com.jordan.pictureapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "pictureItems"
)
data class PictureItem(
    val copyright: String,
    val date: String,
    val description: String,
    @PrimaryKey val hdurl: String,
    val title: String
)