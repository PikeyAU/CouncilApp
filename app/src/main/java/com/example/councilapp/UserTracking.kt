package com.example.councilapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.councilapp.databinding.ActivityUserTrackingBinding
import com.example.councilapp.repository.Reports
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase

class UserTracking : AppCompatActivity() {

    private lateinit var binding: ActivityUserTrackingBinding
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserTrackingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnRetrieve.setOnClickListener {

            val user = FirebaseAuth.getInstance().currentUser
            val uid = user?.uid

            // if (uid != null) {

            //readData(uid)

            //} else {


            //Toast.makeText(this, "You have no submitted reports",Toast.LENGTH_SHORT).show()
            //}
            //}
            Reports.getAllReports(
                //wipFun = {}, // Optional.
                //failFun = {}, // Optional.
                //doneFun = {}, // Optional.
            ) { allReports -> /* `successFun`: NOT optional. Here's where you do what you want to do with
        all the reports you've obtained from Firestore. */
                /* As an example, I'm logging the string representation of the obtained reports. */
                Log.i(TAG, "getAllReportsExample: Obtained all reports $allReports")
            }

        }
    }

    private fun readData(uid: String) {

        database = FirebaseDatabase.getInstance().getReference("reports")
        database.get().addOnSuccessListener {
            if (it.exists()){
                Toast.makeText(this, "Reports exist",Toast.LENGTH_SHORT).show()

            } else{
                Toast.makeText(this, "No reports",Toast.LENGTH_SHORT).show()
            }
        } .addOnFailureListener{
            Toast.makeText(this, "Failed",Toast.LENGTH_SHORT).show()

        }
    }
}

