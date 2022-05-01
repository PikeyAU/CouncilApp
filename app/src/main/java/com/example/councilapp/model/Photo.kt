package com.example.councilapp.model

import com.squareup.moshi.Json

data class Photo (
    @Json(name = "_id") val id: String
)