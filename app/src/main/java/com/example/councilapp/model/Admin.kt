package com.example.councilapp.model

import com.squareup.moshi.Json

data class Admin(
    @Json(name = "_id") val id: String,
    val firstName: String,
    val lastName: String,
)