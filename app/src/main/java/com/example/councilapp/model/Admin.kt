package com.example.councilapp.model

import com.google.firebase.ktx.Firebase
import com.squareup.moshi.Json

data class Admin(
    val uid: String, // Should be a firebase uid string.
    val firstName: String,
    val lastName: String,
)
