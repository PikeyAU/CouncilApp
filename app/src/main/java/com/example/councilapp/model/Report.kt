package com.example.councilapp.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.GeoPoint

data class Report(
    val reportRef: String,
    val reportDate: Timestamp,
    val location: GeoPoint,
    val reportedByUid: String,
    val responsibleAdminUid: String?,
    val reportStatus: String,
    val assetType: String,
    val notes: String,
)