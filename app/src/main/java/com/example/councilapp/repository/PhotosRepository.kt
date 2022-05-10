package com.example.councilapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.councilapp.model.Photo
import com.example.councilapp.network.Firestore
import com.example.councilapp.network.PhotoNetwork
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "PhotosRepository"

class PhotosRepository() {
    private val db = Firebase.firestore
    private val storageRef = Firebase.storage.reference
    private val photosCollection = db.collection("photos")
    var photos = mutableListOf<Photo>()

    init {
        getPhotos()
    }

    fun getPhotos() {
        photosCollection.get().addOnSuccessListener { documents ->
            Log.v(TAG, "================= PhotosRepository =================")
            for (document in documents) {
                val imageId: String = document.id
                var imageUrl: String
                Log.i(TAG, imageId)

                val auth: FirebaseAuth = Firebase.auth
                auth.signInAnonymously().addOnSuccessListener {
                    Log.v(TAG, "================= Anonlymous auth: Success =================")
                    Log.i(TAG, "authId: ${it.toString()}")
                    storageRef.child("images/$imageId.jpg").downloadUrl.addOnSuccessListener { url ->
                        Log.v(TAG, "================= Photos Url: Success =================")
                        imageUrl = url.toString()
                        Log.v(TAG, imageUrl)
                        photos.add(Photo(
                            imageId,
                            imageUrl
                        ))
                        Log.d(TAG, photos.toString())
                    }.addOnFailureListener { e ->
                        Log.v(TAG, "================= Photos Url: Failure =================")
                        Log.e(TAG, "Error obtaining image URL.")
                        Log.e(TAG, e.toString())
                    }.addOnCompleteListener {
                        val currentUser = auth.currentUser
                        if(currentUser!!.isAnonymous)
                            Log.e(TAG, currentUser.uid)
                            currentUser.delete()
                    }
                }
            }
        }
    }

    fun uploadPhoto() {
        val newPhotoRef = photosCollection.document()
    }
}
