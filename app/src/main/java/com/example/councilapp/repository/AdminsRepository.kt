package com.example.councilapp.repository

import android.util.Log
import com.example.councilapp.model.Admin
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "AdminsRepository"

class AdminsRepository {
    private val adminsCollection = Firebase.firestore.collection("admins")
    private var admins = mutableListOf<Admin>()

    init {
        /*
         */
        val auth = Firebase.auth
        auth.signInAnonymously().addOnSuccessListener {
            addAdmin(firstName = "Tim", lastName = "Baker", uid = auth.currentUser!!.uid)
        }.addOnCompleteListener {
            auth.currentUser!!.delete()
        }
        getAllAdmins {
            Log.v(TAG, it.toString())
        }
    }

    /**
     * @param[uid] should be a FirebaseUser uid.
     */
    private fun addAdmin(
        uid: String,
        firstName: String,
        lastName: String,
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: () -> Any = {},
    ) {
        val newAdmin = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
        )
        adminsCollection.document(uid)
            .set(newAdmin)
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

    private fun getAdmin(
        uid: String,
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: (Admin) -> Any,
    ) {
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

    private fun getAllAdmins(
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: (List<Admin>) -> Any,
    ) {
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

    private fun deleteAdmin(
        uid: String,
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: () -> Any = {},
    ) {
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