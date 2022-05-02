package com.example.councilapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.councilapp.model.Photo
import com.example.councilapp.network.PhotoNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhotosRepository() {
    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>> = _photos
    suspend fun getPhotos() {
        withContext(Dispatchers.IO) {
            _photos.value = PhotoNetwork.retrofitService.getPhotos()
        }
    }
}
