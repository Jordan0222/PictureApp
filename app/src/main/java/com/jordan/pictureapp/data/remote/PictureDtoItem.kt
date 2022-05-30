package com.jordan.pictureapp.data.remote

import com.jordan.pictureapp.domain.model.PictureItem

data class PictureDtoItem(
    val apod_site: String,
    val copyright: String,
    val date: String,
    val description: String,
    val hdurl: String,
    val media_type: String,
    val title: String,
    val url: String
) {
    fun toPictureItem(): PictureItem {
        return PictureItem(
            copyright = copyright,
            date = date,
            description = description,
            hdurl = hdurl,
            title = title
        )
    }
}