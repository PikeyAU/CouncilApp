package com.example.councilapp.repository

import android.util.Log
import com.example.councilapp.model.Report
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

private const val TAG = "ReportsRepository"

object Reports{
    private val reportsCollection = Firebase.firestore.collection("reports")
    private val residentsCollection = Firebase.firestore.collection("residents")
    private val reportCountDocument = Firebase.firestore.collection("global").document("report_count")
    private var reports = mutableListOf<Report>()

    private fun increaseReportCount(
        failFun: (Exception) -> Any = {},
        successFun: (Int) -> Any = {},
    ) {
        reportCountDocument
            .get()
            .addOnSuccessListener {
                val newReportCount = (it.get("currentCount") as Long).toInt() + 1
                reportCountDocument
                    .set(hashMapOf("currentCount" to newReportCount))
                    .addOnSuccessListener { successFun(newReportCount) }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "================= addReport: failed =================")
                        Log.e(TAG, "Error adding new report: $it")
                        failFun(e)
                    }
            }
    }

    fun addReport(
        location: GeoPoint,
        reportedByUid: String,
        reportStatus:  String,
        assetType: String,
        notes: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: (String) -> Any = {},
    ) {
        wipFun()
        increaseReportCount({
            Log.e(TAG, "================= addReport: failed =================")
            Log.e(TAG, "Error adding new report: $it")
            failFun(it)
            doneFun()
        }) { allocatedRefNum ->
            val reportRef = "RPT" + allocatedRefNum.toString().padStart(5, '0')
            reportsCollection.document(reportRef)
                .set(hashMapOf(
                    "reportDate" to Timestamp(Date()),
                    "location" to location,
                    "reportedBy" to reportedByUid,
                    "assetType" to assetType,
                    "notes" to notes,
                    "status" to reportStatus,
                ))
                .addOnSuccessListener { successFun(reportRef) }
                .addOnFailureListener {
                    Log.e(TAG, "================= addReport: failed =================")
                    Log.e(TAG, "Error adding new report:$it")
                    failFun(it)
                }
                .addOnCompleteListener { doneFun() }
        }
    }

    fun changeReportAdmin(
        reportRef: String,
        adminUid: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: () -> Any = {},
    ) {
        wipFun()
        reportsCollection.document(reportRef)
            .update("responsibleAdmin", adminUid)
            .addOnSuccessListener { successFun() }
            .addOnFailureListener {
                Log.e(TAG, "================= changeReportAdmin: failed =================")
                Log.e(TAG, "Error changing report admin:$it")
                failFun(it)
            }
            .addOnCompleteListener { doneFun() }
    }

    fun changeReportStatus(
        reportRef: String,
        newStatus: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: () -> Any = {},
    ) {
        wipFun()
        reportsCollection.document(reportRef)
            .update("status", newStatus)
            .addOnSuccessListener { successFun() }
            .addOnFailureListener {
                Log.e(TAG, "================= changeReportStatus: failed =================")
                Log.e(TAG, "Error changing report status:$it")
                failFun(it)
            }
            .addOnCompleteListener { doneFun() }
    }

    fun getReport(
        reportRef: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: (Report) -> Any,
    ) {
        wipFun()
        val reportDoc = reportsCollection.document(reportRef)
        reportDoc.get()
            .addOnSuccessListener {
                successFun(Report(
                    reportRef = it.id,
                    reportDate = it.get("reportDate") as Timestamp,
                    location = it.get("location") as GeoPoint,
                    assetType = it.get("assetType") as String,
                    notes = it.get("notes") as String,
                    reportedByUid = it.get("reportedBy") as String,
                    responsibleAdminUid = it.get("responsibleAdmin") as String?,
                    reportStatus = it.get("status") as String,
                ))
            }
            .addOnFailureListener {
                Log.e(TAG, "================= getReport: failed =================")
                Log.e(TAG, "Error getting report:$it")
                failFun(it)
            }
            .addOnCompleteListener { doneFun() }
    }

    fun getAllReports(
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: (List<Report>) -> Any,
    ) {
        wipFun()
        reportsCollection.get()
            .addOnSuccessListener {
                for (document in it) {
                    reports.add(Report(
                        reportRef = document.id,
                        reportDate = document.get("reportDate") as Timestamp,
                        location = document.get("location") as GeoPoint,
                        reportedByUid = document.get("reportedBy") as String,
                        responsibleAdminUid = document.get("responsibleAdmin") as String?,
                        reportStatus = document.get("status") as String,
                        assetType = document.get("assetType") as String,
                        notes = document.get("notes") as String,
                    ))
                }
                successFun(reports)
            }.addOnFailureListener {
                Log.e(TAG, "================= getAllReports: failed =================")
                Log.e(TAG, "Error getting all reports:$it")
                failFun(it)
            }.addOnCompleteListener {
                doneFun()
            }
    }

    fun deleteReport(
        reportRef: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: () -> Any = {},
    ) {
        wipFun()
        reportsCollection.document(reportRef)
            .delete()
            .addOnSuccessListener {
                successFun()
            }
            .addOnFailureListener {
                Log.e(TAG, "================= deleteReport: failed =================")
                Log.e(TAG, "Error deleting report: $it")
                failFun(it)
            }
            .addOnCompleteListener {
                doneFun()
            }
    }
}

