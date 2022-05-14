package com.example.councilapp.repository

import android.net.Uri
import android.util.Log
import com.example.councilapp.model.Photo
import com.example.councilapp.repository.Photos.photos
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

private const val TAG = "PhotosRepository"

object Photos {
    private val photoStorage = Firebase.storage.reference.child("photos")
    private val reportsCollection = Firebase.firestore.collection("reports")
    var photos = mutableListOf<Photo>()

    private fun authenticateAnonymously(
        failFun: (Exception) -> Any = {},
        successFun: () -> Any = {},
    ) {
        Firebase.auth.signInAnonymously()
            .addOnSuccessListener { successFun() }
            .addOnFailureListener {
                Log.e(TAG, "================= authenticateAnonymously: failed =================")
                Log.e(TAG, "Error obtaining firebase storage authentication: $it")
                failFun(it)
            }
    }

    private fun uploadPhotoAndGetUrl(
        photoFileName: String,
        photoUri: Uri,
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: (Uri) -> Any,
    ) {
        val photoReference = photoStorage.child(photoFileName)
        photoReference.putFile(photoUri)
            .addOnSuccessListener {
                photoReference.downloadUrl
                    .addOnSuccessListener { successFun(it) }
                    .addOnFailureListener {
                        Log.e(TAG, "================= uploadPhoto: failed =================")
                        Log.e(TAG, "Error obtaining photo URL: $it")
                        photoReference.delete()
                        failFun(it)
                        doneFun()
                    }
            }
            .addOnFailureListener {
                // Delete the passed in firebase document.
                Log.e(TAG, "================= uploadPhoto: failed =================")
                Log.e(TAG, "Error uploading photo to firebase storage: $it")
                failFun(it)
                doneFun()
            }
    }

    fun addPhoto(
        reportRef: String,
        filePath: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: (String) -> Any = {},
    ) {
        wipFun()
        val photoFile = File(filePath)
        // Determine file type extension.
        val photoExtension = photoFile.extension

        val photoDocument = reportsCollection.document(reportRef).collection("photos").document()
        // Determine file name without extension.
        val photoId = photoDocument.id

        val photoFileName = "$photoId.$photoExtension"

        if (Firebase.auth.currentUser != null) {
            uploadPhotoAndGetUrl(
                photoFileName,
                Uri.fromFile(photoFile),
                failFun,
                doneFun,
            ) { url ->
                photoDocument
                    .set(hashMapOf(
                        "fileName" to photoFileName,
                        "url" to url.toString()
                    ))
                    .addOnSuccessListener { successFun(photoId) }
                    .addOnFailureListener {
                        Log.e(TAG, "================= addPhoto: failed =================")
                        Log.e(TAG, "Error adding photo: $it")
                        failFun(it)
                    }
                    .addOnCompleteListener { doneFun() }
            }
        }
        else {
            authenticateAnonymously({
                photoDocument.delete()
                failFun(it)
                doneFun()
            }) {
                uploadPhotoAndGetUrl(
                    photoFileName,
                    Uri.fromFile(photoFile),
                    failFun,
                    {
                        Firebase.auth.currentUser!!.delete()
                        doneFun()
                    },
                ) { url ->
                    photoDocument
                        .set(hashMapOf(
                            "fileName" to photoFileName,
                            "url" to url.toString()
                        ))
                        .addOnSuccessListener { successFun(photoId) }
                        .addOnFailureListener {
                            Log.e(TAG, "================= addPhoto: failed =================")
                            Log.e(TAG, "Error adding photo: $it")
                            failFun(it)
                        }
                        .addOnCompleteListener {
                            Firebase.auth.currentUser!!.delete()
                            doneFun()
                        }
                }
            }
        }
    }

    fun getPhoto(
        reportRef: String,
        photoId: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: (Photo) -> Any = {},
    ) {
        wipFun();
        reportsCollection.document("$reportRef/photos/$photoId")
            .get()
            .addOnSuccessListener {
                    successFun(Photo(
                        it.id,
                        it.get("fileName") as String,
                        it.get("url") as String,
                    ))
            }.addOnFailureListener {
                Log.e(TAG, "================= getReportPhotos: Failure =================")
                Log.e(TAG, "Error obtaining report photos: $it")
                failFun(it)
            }.addOnCompleteListener { doneFun() }
    }

    fun getReportAllPhotos(
        reportRef: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: (List<Photo>) -> Any,
    ) {
        wipFun();
        reportsCollection.document(reportRef).collection("photos")
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    photos.add(Photo(
                        document.id,
                        document.get("fileName") as String,
                        document.get("url") as String,
                    ))
                }
                successFun(photos)
            }.addOnFailureListener {
                Log.e(TAG, "================= getReportPhotos: Failure =================")
                Log.e(TAG, "Error obtaining report photos: $it")
                failFun(it)
            }.addOnCompleteListener { doneFun() }
    }

    fun deletePhoto (
        reportRef: String,
        photoId: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: () -> Any = {},
    ) {
        wipFun()
        getPhoto(
            reportRef,
            photoId,
            failFun = {
                Log.e(TAG, "================= deletePhoto: Failure =================")
                Log.e(TAG, "Error deleting photo: $it")
                failFun(it)
                doneFun()
            }
        ) { photo ->
            reportsCollection.document("$reportRef/photos/${photo.id}")
                .delete()
                .addOnSuccessListener {
                    if(Firebase.auth.currentUser != null){
                        photoStorage.child(photo.fileName).delete()
                    }
                    else {
                        authenticateAnonymously {
                            photoStorage.child(photo.fileName)
                                .delete()
                                .addOnCompleteListener { Firebase.auth.currentUser!!.delete() }
                        }
                    }
                    successFun()
                }
                .addOnFailureListener {
                    Log.e(TAG, "================= deletePhoto: Failure =================")
                    Log.e(TAG, "Error deleting photo: $it")
                    failFun(it)
                }
                .addOnCompleteListener { doneFun() }
        }
    }
}
