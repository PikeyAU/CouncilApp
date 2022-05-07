package com.example.councilapp.model

import com.squareup.moshi.Json

data class Resident(
   @Json(name = "_id") val id: String,
   val firstName: String,
   val lastName: String,
   val email: String,
)