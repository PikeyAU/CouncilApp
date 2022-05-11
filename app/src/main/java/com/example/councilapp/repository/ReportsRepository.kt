package com.example.councilapp.repository

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.councilapp.model.Report as Report

private const val TAG = "ReportsRepository"

class ReportsRepository() {
    private val db = Firebase.firestore
    private val reportsCollection = db.collection("reports")
    var reports = mutableListOf<Report>()

    init {
        getReports()
    }

    fun getReports() {
        reportsCollection.get().addOnSuccessListener { documents ->
            Log.v(TAG, "================= ReportsRepository =================")
            Log.e(TAG, reportsCollection.toString())
            for (document in documents) {
                val id = document.id
                val location = document.getGeoPoint("location")
                val reportDate = document.getTimestamp("reportDate")
                Log.i(TAG, document.toString())
                Log.i(TAG, document.getDate("reportDate").toString())
                Log.i(TAG, document.getTimestamp("reportDate").toString())
                Log.i(TAG, document.getGeoPoint("location").toString())
                db.collection("reports/$id/photos").get().addOnSuccessListener { docs ->
                    Log.v(TAG, "================= ReportsRepository/photos subcollection =================")
                    for (d in docs) {
                        Log.e(TAG, d.toString())
                    }
                }
                /*
                reports.add(Report(
                    "${document.id}",
                    "${document.data["reportDate"]}",
                    "${document.data["location"]}",
                    "${document.data["reportedBy"]}",
                    "${document.data["responsibleAdmin"]}",
                    "${document.data["status"]}",
                    "${document.data["photos"]}",
                    "${document.data["comments"]}",
                ))
                */
            }
            //Log.d(TAG, reports.toString())
        }
    }

    fun addReport() {
        val nowTime = FieldValue.serverTimestamp()
        val newReportData = hashMapOf(
            "stringExample" to "Hello world!",
            "booleanExample" to true,
            "numberExample" to 3.14159265,
            "dateExample" to FieldValue.serverTimestamp(),
            "listExample" to arrayListOf(1, 2, 3),
            "nullExample" to null,
        )
    }
}
