package com.example.councilapp.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

data class Comment(
    val id: String,
    val commenter: DocumentReference,
    val commentDate: Timestamp,
    val forReportStatus: String,
    val commentText: String,
)