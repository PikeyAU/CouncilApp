package com.example.councilapp.model

data class Report(
    val refNum: String,
    val reportDate: String,
    val location: String,
    val reportedBy: String,
    val responsibleAdmin: String,
    val status: String,
    val photos: String,
    val comments: String,
)