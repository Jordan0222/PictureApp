package com.jordan.pictureapp.data.remote

import retrofit2.http.GET

interface PictureApi {

    @GET("/robert0ng/NasaDataSet/main/apod.json")
    suspend fun getPictureItems(): PictureDto
}