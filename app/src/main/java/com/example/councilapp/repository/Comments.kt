package com.example.councilapp.repository

import android.util.Log
import com.example.councilapp.model.Comment
import com.example.councilapp.repository.Comments.comments
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

private const val TAG = "CommentsRepository"

object Comments{
    private val reportsCollection = Firebase.firestore.collection("reports")
    var comments = mutableListOf<Comment>()

    fun addComment(
        reportRef: String,
        reportStatus: String,
        commenterUid: String,
        commentText: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: (String) -> Any = {},
    ) {
        wipFun()
        val newCommentDoc = reportsCollection.document(reportRef).collection("comments").document()
        newCommentDoc
            .set(hashMapOf(
                "commenter" to commenterUid,
                "commentDate" to Timestamp(Date()),
                "forReportStatus" to reportStatus,
                "commentText" to commentText,
            ))
            .addOnSuccessListener { successFun(newCommentDoc.id) }
            .addOnFailureListener {
                Log.e(TAG, "================= addComment: failed =================")
                Log.e(TAG, "Error adding comment: $it")
                failFun(it)
            }
            .addOnCompleteListener { doneFun() }
    }

    fun getComment(
        reportRef: String,
        commentId: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: (Comment) -> Any = {},
    ) {
        wipFun()
        reportsCollection.document("$reportRef/comments/$commentId")
            .get()
            .addOnSuccessListener {
                successFun(Comment(
                    it.id,
                    it.get("commenter") as String,
                    it.get("commentDate") as Timestamp,
                    it.get("forReportStatus") as String,
                    it.get("commentText") as String,
                ))
            }.addOnFailureListener {
                Log.e(TAG, "================= getReportComments: Failure =================")
                Log.e(TAG, "Error obtaining report comments: $it")
                failFun(it)
            }.addOnCompleteListener { doneFun() }
    }

    fun getReportAllComments(
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
                        document.get("commenter") as String,
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