package com.example.councilapp.repository

import android.util.Log
import com.example.councilapp.model.Admin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception

private const val TAG = "AdminsRepository"

class AdminsRepository {
    private val db = Firebase.firestore
    private val adminsCollection = db.collection("admins")
    private var admins = mutableListOf<Admin>()

    init {
        /*
         */
        val auth = Firebase.auth
        auth.signInAnonymously().addOnSuccessListener {
            addAdmin(firstName = "Tim", lastName = "Baker", uid = auth.currentUser!!.uid)
            auth.currentUser!!.delete()
        }
        getAllAdmins {
            Log.v(TAG, it.toString())
        }
    }

    /**
     * @param[uid] should be a FirebaseUser uid.
     */
    fun addAdmin(uid: String, firstName: String, lastName: String) {
        val newAdmin = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
        )
        adminsCollection.document(uid)
            .set(newAdmin)
            .addOnSuccessListener {
                Log.v(TAG, "================= addAdmin: successful =================")
                Log.v(TAG, "New admin $uid: $firstName $lastName was added successfully.")
            }
            .addOnFailureListener {
                Log.e(TAG, "================= addAdmin: failed =================")
                Log.e(TAG, "Error adding new admin: $it")
            }
    }

    fun getAdmin(
        uid: String,
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {println(it)},
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
                failFun(it)
            }
            .addOnCompleteListener {
                doneFun()
            }
    }

    fun getAllAdmins(
        wipFun: () -> Any = {},
        failFun: (Exception) -> Any = {println(it)},
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
                failFun(it)
            }.addOnCompleteListener {
                doneFun()
            }
    }

    fun deleteAdmin(uid: String) {
        adminsCollection.document(uid)
            .delete()
            .addOnSuccessListener {
                Log.v(TAG, "================= deleteAdmin: successful =================")
                Log.v(TAG, "Admin $uid was deleted successfully.")
            }
            .addOnFailureListener {
                Log.e(TAG, "================= deleteAdmin: failed =================")
                Log.e(TAG, "Error deleting admin: $it")
            }
    }
}