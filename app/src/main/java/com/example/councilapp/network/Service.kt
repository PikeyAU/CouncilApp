package com.example.councilapp.network

import com.example.councilapp.model.Photo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// TODO: Complete base url.
private const val BASE_URL = "https://"

/**
 * Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * The Retrofit object with the Moshi converter.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the [getPhotos] method
 */
interface PhotoService {
    @GET("photos")
    suspend fun getPhotos(): List<Photo>
}

/**
 * A public Api object that exposes the lazy-initialised Retrofit service
 */
object PhotoNetwork {
    val retrofitService: PhotoService by lazy {
        retrofit.create(PhotoService::class.java)
    }
}