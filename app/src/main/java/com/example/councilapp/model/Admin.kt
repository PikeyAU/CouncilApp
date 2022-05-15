package com.example.councilapp.model

data class Admin(
    val uid: String, // Should be a firebase uid string.
    val fullName: String,
    val dateOfBirth: String?,
    val phoneNumber: String?,
    val address: String?,
    val city: String?,
    val state: String?,
    val postcode: String?,
)