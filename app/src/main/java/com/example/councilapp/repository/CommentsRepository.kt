package com.example.councilapp.repository

import android.util.Log
import com.example.councilapp.model.Comment
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

private const val TAG = "CommentsRepository"

class CommentsRepository {
    private val reportsCollection = Firebase.firestore.collection("reports")
    var comments = mutableListOf<Comment>()

    init {

    }

    fun addComment(
        reportRef: String,
        reportStatus: String,
        commenter: DocumentReference,
        commentText: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: () -> Any = {},
    ) {
        wipFun()
        reportsCollection.document(reportRef).collection("comments")
            .add(hashMapOf(
                "commenter" to commenter,
                "commentDate" to Timestamp(Date()),
                "forReportStatus" to reportStatus,
                "commentText" to commentText,
            ))
            .addOnSuccessListener { successFun() }
            .addOnFailureListener {
                Log.e(TAG, "================= addComment: failed =================")
                Log.e(TAG, "Error adding comment: $it")
                failFun(it)
            }
            .addOnCompleteListener { doneFun() }
    }

    fun getReportComments(
        reportRef: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: (List<Comment>) -> Any,
    ) {
        wipFun()
        reportsCollection.document(reportRef).collection("comments")
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    comments.add(Comment(
                        document.id,
                        document.get("commenter") as DocumentReference,
                        document.get("commentDate") as Timestamp,
                        document.get("forReportStatus") as String,
                        document.get("commentText") as String,
                    ))
                }
                successFun(comments)
            }.addOnFailureListener {
                Log.e(TAG, "================= getReportComments: Failure =================")
                Log.e(TAG, "Error obtaining report comments: $it")
                failFun(it)
            }.addOnCompleteListener { doneFun() }
    }

    fun deleteComment(
        reportRef: String,
        commentId: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: () -> Any = {},
    ) {
        wipFun()
        reportsCollection.document(reportRef).collection("comments").document(commentId)
            .delete()
            .addOnSuccessListener { successFun() }
            .addOnFailureListener {
                Log.e(TAG, "================= deleteComment: Failure =================")
                Log.e(TAG, "Error deleting comment: $it")
                failFun(it)
            }
            .addOnCompleteListener { doneFun() }
    }
}