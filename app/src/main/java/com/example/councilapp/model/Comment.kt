package com.example.councilapp.model

import com.squareup.moshi.Json

data class Comment(
    //@Json(name = "_id") val id: String,
    val id: String,
    val commenter: String,
    val commentDate: String,
    val forReportStatus: String,
    val isInternal: String,
    val content: String,
)