package com.example.councilapp.model

data class Admin(
    val uid: String, // Should be a firebase uid string.
    val firstName: String,
    val lastName: String,
)