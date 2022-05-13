package com.example.councilapp.repository

import android.util.Log
import com.example.councilapp.model.Resident
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "ResidentsRepository"

class ResidentsRepository {
    private val residentsCollection = Firebase.firestore.collection("residents")
    private var residents = mutableListOf<Resident>()

    init {
        /*
         */
        val auth = Firebase.auth
        auth.signInAnonymously().addOnSuccessListener {
            addResident(firstName = "Tim", lastName = "Baker", emailAddress="tim.baker@fake.email.io", uid = auth.currentUser!!.uid)
        }.addOnCompleteListener {
            auth.currentUser!!.delete()
        }
        getAllResidents {
            Log.v(TAG, it.toString())
        }
    }

    /**
     * @param[uid] should be a FirebaseUser uid.
     */
    fun addResident(
        uid: String,
        firstName: String,
        lastName: String,
        emailAddress: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {},
        doneFun: () -> Any = {},
        successFun: () -> Any = {},
    ) {
        wipFun()
        residentsCollection.document(uid)
            .set(hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
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
                    firstName = it.get("firstName") as String,
                    lastName = it.get("lastName") as String,
                    emailAddress = it.get("emailAddress") as String,
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
                        firstName = document.get("firstName") as String,
                        lastName = document.get("lastName") as String,
                        emailAddress = document.get("emailAddress") as String,
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
}