package com.example.councilapp.repository

import android.util.Log
import com.example.councilapp.model.Admin
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "AdminsRepository"

object Admins {
    private val adminsCollection = Firebase.firestore.collection("admins")
    private var admins = mutableListOf<Admin>()

    /**
     * @param[uid] should be a FirebaseUser uid.
     */
    fun addAdmin(
        uid: String,
        fullName: String,
        dateOfBirth: String? = null,
        phoneNumber: String? = null,
        address: String? = null,
        city: String? = null,
        state: String? = null,
        postcode: String? = null,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: () -> Any = {},
    ) {
        wipFun()
        adminsCollection.document(uid)
            .set(hashMapOf(
                "fullName" to fullName,
                "date of birth" to dateOfBirth,
                "phone number" to phoneNumber,
                "address" to address,
                "city" to city,
                "state" to state,
                "postcode" to postcode,
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
                    fullName = it.get("fullName") as String,
                    dateOfBirth = it.get("date of birth") as String?,
                    phoneNumber = it.get("phone number") as String?,
                    address = it.get("address") as String?,
                    city = it.get("city") as String?,
                    state = it.get("state") as String?,
                    postcode = it.get("postcode") as String?,
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
                        fullName = document.get("fullName") as String,
                        dateOfBirth = document.get("date of birth") as String?,
                        phoneNumber = document.get("phone number") as String?,
                        address = document.get("address") as String?,
                        city = document.get("city") as String?,
                        state = document.get("state") as String?,
                        postcode = document.get("postcode") as String?,
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