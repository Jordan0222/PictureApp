package com.jordan.pictureapp.di

import android.app.Application
import androidx.room.Room
import com.jordan.pictureapp.data.local.PictureDatabase
import com.jordan.pictureapp.data.local.PictureDatabase.Companion.DATABASE_NAME
import com.jordan.pictureapp.data.remote.PictureApi
import com.jordan.pictureapp.data.repository.PictureRepositoryImpl
import com.jordan.pictureapp.domain.repository.PictureRepository
import com.jordan.pictureapp.util.Constant.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePictureDatabase(app: Application): PictureDatabase {
        return Room.databaseBuilder(
            app,
            PictureDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePictureApi(): PictureApi {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providePictureRepository(
        api: PictureApi,
        db: PictureDatabase
    ): PictureRepository {
        return PictureRepositoryImpl(api, db.pictureDao)
    }
}