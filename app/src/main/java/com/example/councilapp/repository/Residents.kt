package com.example.councilapp.repository

import android.util.Log
import com.example.councilapp.model.Resident
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "ResidentsRepository"

object Residents{
    private val residentsCollection = Firebase.firestore.collection("residents")
    private var residents = mutableListOf<Resident>()

    /**
     * @param[uid] should be a FirebaseUser uid.
     */
    fun addResident(
        uid: String,
        fullName: String,
        dateOfBirth: String? = null,
        phoneNumber: String? = null,
        address: String? = null,
        city: String? = null,
        state: String? = null,
        postcode: String? = null,
        emailAddress: String? = null,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: () -> Any = {},
    ) {
        wipFun()
        residentsCollection.document(uid)
            .set(hashMapOf(
                "fullName" to fullName,
                "date of birth" to dateOfBirth,
                "phone number" to phoneNumber,
                "address" to address,
                "city" to city,
                "state" to state,
                "postcode" to postcode,
                "emailAddress" to emailAddress,
            ))
            .addOnSuccessListener {
                successFun()
            }
            .addOnFailureListener {
                Log.e(TAG, "================= addResident: failed =================")
                Log.e(TAG, "Error adding new resident: $it")
                failFun(it)
            }
            .addOnCompleteListener {
                doneFun()
            }
    }

    fun getResident(
        uid: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: (Resident) -> Any,
    ) {
        wipFun()
        residentsCollection.document(uid)
            .get()
            .addOnSuccessListener {
                successFun(Resident(
                    uid = it.id,
                    fullName = it.get("fullName") as String,
                    dateOfBirth = it.get("date of birth") as String?,
                    phoneNumber = it.get("phone number") as String?,
                    address = it.get("address") as String?,
                    city = it.get("city") as String?,
                    state = it.get("state") as String?,
                    postcode = it.get("postcode") as String?,
                    emailAddress = it.get("emailAddress") as String?,
                ))
            }
            .addOnFailureListener {
                Log.e(TAG, "================= getResident: failed =================")
                Log.e(TAG, "Error getting resident: $it")
                failFun(it)
            }
            .addOnCompleteListener {
                doneFun()
            }
    }

    fun getAllResidents(
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: (List<Resident>) -> Any,
    ) {
        wipFun()
        residentsCollection.get()
            .addOnSuccessListener {
                for (document in it) {
                    residents.add(Resident(
                        uid = document.id,
                        fullName = document.get("fullName") as String,
                        dateOfBirth = document.get("date of birth") as String?,
                        phoneNumber = document.get("phone number") as String?,
                        address = document.get("address") as String?,
                        city = document.get("city") as String?,
                        state = document.get("state") as String?,
                        postcode = document.get("postcode") as String?,
                        emailAddress = document.get("emailAddress") as String?,
                    ))
                }
                successFun(residents)
            }.addOnFailureListener {
                Log.e(TAG, "================= getAllResidents: failed =================")
                Log.e(TAG, "Error getting all residents: $it")
                failFun(it)
            }.addOnCompleteListener {
                doneFun()
            }
    }

    fun deleteResident(
        uid: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: () -> Any = {},
    ) {
        wipFun()
        residentsCollection.document(uid)
            .delete()
            .addOnSuccessListener {
                successFun()
            }
            .addOnFailureListener {
                Log.e(TAG, "================= deleteResident: failed =================")
                Log.e(TAG, "Error deleting resident: $it")
                failFun(it)
            }
            .addOnCompleteListener {
                doneFun()
            }
    }

    /* I don't think this is needed for this school project.
    fun updateResident(
        uid: String,
        fieldName: String,
        newValue: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: () -> Any = {},
    ) {
        wipFun()
        residentsCollection.document(uid)
            .update(fieldName, newValue)
            .addOnSuccessListener {
                successFun()
            }
            .addOnFailureListener {
                Log.e(TAG, "================= updateResident: failed =================")
                Log.e(TAG, "Error updating resident: $it")
                failFun(it)
            }
            .addOnCompleteListener {
                doneFun()
            }
    }
     */
}