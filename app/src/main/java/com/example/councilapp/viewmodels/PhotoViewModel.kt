package com.example.councilapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.councilapp.repository.PhotosRepository
import kotlinx.coroutines.launch
import java.io.IOException

class PhotoViewModel : ViewModel() {

    private val photosRepository = PhotosRepository()
    val photos = photosRepository.photos

    /**
     * Event triggered for network error. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    /**
     * Event triggered for network error. Views should use this to get access
     * to the data.
     */
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    /**
     * Flag to display the error message. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    /**
     * Flag to display the error message. Views should use this to get access
     * to the data.
     */
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    /**
     * Call getPhotosFromRepository as soon as this ViewModel is created.
     */
    init {
        getPhotosFromRepository()
    }

    /**
     * Get photos from the repository. Use a coroutine launch to run in a
     * background thread.
     */
    private fun getPhotosFromRepository() {
        viewModelScope.launch {
            try {
                photosRepository.getPhotos()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (networkError: IOException) {
                if(photos.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    /**
     * Resets the network error flag.
     */
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }
}
