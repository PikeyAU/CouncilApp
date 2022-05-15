package com.example.councilapp.model

data class Resident(
   val uid: String,
   val fullName: String,
   val dateOfBirth: String?,
   val phoneNumber: String?,
   val address: String?,
   val city: String?,
   val state: String?,
   val postcode: String?,
   val emailAddress: String?,
)