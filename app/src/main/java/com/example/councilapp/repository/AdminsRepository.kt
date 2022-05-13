package com.example.councilapp.repository

import android.util.Log
import com.example.councilapp.model.Admin
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "AdminsRepository"

class AdminsRepository {
    private val adminsCollection = Firebase.firestore.collection("admins")
    private var admins = mutableListOf<Admin>()

    init {
    }

    /**
     * @param[uid] should be a FirebaseUser uid.
     */
    fun addAdmin(
        uid: String,
        firstName: String,
        lastName: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: () -> Any = {},
    ) {
        wipFun()
        adminsCollection.document(uid)
            .set(hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            ))
            .addOnSuccessListener {
                successFun()
            }
            .addOnFailureListener {
                Log.e(TAG, "================= addAdmin: failed =================")
                Log.e(TAG, "Error adding new admin:$it")
                failFun(it)
            }
            .addOnCompleteListener {
                doneFun()
            }
    }

    fun getAdmin(
        uid: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: (Admin) -> Any,
    ) {
        wipFun()
        adminsCollection.document(uid)
            .get()
            .addOnSuccessListener {
                successFun(Admin(
                    uid = it.id,
                    firstName = it.get("firstName") as String,
                    lastName = it.get("lastName") as String,
                ))
            }
            .addOnFailureListener {
                Log.e(TAG, "================= getAdmin: failed =================")
                Log.e(TAG, "Error getting admin:$it")
                failFun(it)
            }
            .addOnCompleteListener {
                doneFun()
            }
    }

    fun getAllAdmins(
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: (List<Admin>) -> Any,
    ) {
        wipFun()
        adminsCollection.get()
            .addOnSuccessListener {
                for (document in it) {
                    admins.add(Admin(
                        uid = document.id,
                        firstName = document.get("firstName") as String,
                        lastName = document.get("lastName") as String
                    ))
                }
                successFun(admins)
            }.addOnFailureListener {
                Log.e(TAG, "================= getAllAdmins: failed =================")
                Log.e(TAG, "Error getting all admins:$it")
                failFun(it)
            }.addOnCompleteListener {
                doneFun()
            }
    }

    fun deleteAdmin(
        uid: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: () -> Any = {},
    ) {
        wipFun()
        adminsCollection.document(uid)
            .delete()
            .addOnSuccessListener {
                successFun()
            }
            .addOnFailureListener {
                Log.e(TAG, "================= deleteAdmin: failed =================")
                Log.e(TAG, "Error deleting admin: $it")
                failFun(it)
            }
            .addOnCompleteListener {
                doneFun()
            }
    }
}