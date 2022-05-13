package com.example.councilapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.councilapp.repository.AdminsRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "REPOSITORIES_EXAMPLES"

class RepositoryExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addAdminExample()
    }

    private fun addAdminExample() {
        val admins = AdminsRepository()
        val randomId = Firebase.firestore.collection("global").document().id /* You
        DON'T need to do this! */

        admins.addAdmin(
            uid = randomId, /* You should use `Firebase.auth.currentUser.uid` to get the
            actual uid of the current user. */
            firstName = "Jone",
            lastName = "Doe",
            wipFun = { /* This parameter is optional. It will be executed before the method talks
            to firebase. */
                Log.i(TAG, "Adding new admin...") /* As an example, I logged a messaged here,
                but you can do whatever you want. */
            },
            failFun = { /* Also optional. This will be executed if the method fails to add a new
            admin in firebase. */
                Log.i(TAG, "Adding new admin failed.") /* Again, do whatever you want here. */
            },
            doneFun = { /* Also optional. This will be executed as the final step regardless of
            whether the new admin is added successfully or not. */
                Log.i(TAG, "Method execution has finished.") /* Again, do whatever you want
                here. */
            },
        ) { /* Also optional. It will be executed if adding the new admin is successful. */
            /* Again, do whatever you want in here. I logged the string representation of the new
            *  admin here as an example.
            * */
            admins.getAdmin(randomId) {
                Log.i(TAG, "Successfully added new admin: $it")
            }
        }
    }
}