package com.example.councilapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.councilapp.Remote.RemoteApi
import com.example.councilapp.model.Photo
import kotlinx.coroutines.launch
import java.lang.Exception

class PhotosRepository(
    private val photosRemoteDataSource: PhotosNetworkDataSource) {
    // TODO:  
}

enum class RemoteApiStatus { LOADING, ERROR, DONE }

class PhotosNetworkDataSource : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<RemoteApiStatus>()

    // The internal MutableLiveData that stores a photo data instance
    private val _photos = MutableLiveData<List<Photo>>()

    // The external immutable LiveData for the photo
    val photos: LiveData<List<Photo>> = _photos

    // The external immutable LiveData for the request status
    val status: LiveData<RemoteApiStatus> = _status
    /**
     * Call getPhotos() on init so we can display status immediately.
     */
    init {
        getPhotos()
    }

    /**
     * Gets photos information from the API Retrofit service and updates the
     * [Photo] [List] [LiveData].
     */
    private fun getPhotos() {
        viewModelScope.launch {
            _status.value = RemoteApiStatus.LOADING
            try {
                _photos.value = RemoteApi.retrofitService.getPhotos()
                _status.value = RemoteApiStatus.DONE
            } catch (e: Exception) {
                _status.value = RemoteApiStatus.ERROR
                _photos.value = listOf()
            }
        }
    }
}
