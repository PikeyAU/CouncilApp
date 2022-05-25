package com.example.councilapp

import android.os.Bundle
import android.util.Log
import android.widget.EditText
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserTracking : AppCompatActivity() {

    private lateinit var binding: ActivityUserTrackingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserTrackingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnRetrieve.setOnClickListener {

            val user = FirebaseAuth.getInstance().currentUser
            val uid = user?.uid

            val location_1 = findViewById<TextView>(R.id.textView11)
            val location_2 = findViewById<TextView>(R.id.textView21)
            val location_3 = findViewById<TextView>(R.id.textView31)
            val location_4 = findViewById<TextView>(R.id.textView41)
            val date_1 = findViewById<TextView>(R.id.textView12)
            val date_2 = findViewById<TextView>(R.id.textView22)
            val date_3 = findViewById<TextView>(R.id.textView32)
            val date_4 = findViewById<TextView>(R.id.textView42)
            val status_1 = findViewById<TextView>(R.id.textView13)
            val status_2 = findViewById<TextView>(R.id.textView23)
            val status_3 = findViewById<TextView>(R.id.textView33)
            val status_4 = findViewById<TextView>(R.id.textView43)
            val feed_1 = findViewById<TextView>(R.id.textView14)
            val feed_2 = findViewById<TextView>(R.id.textView24)
            val feed_3 = findViewById<TextView>(R.id.textView34)
            val feed_4 = findViewById<TextView>(R.id.textView44)

            binding.btnRetrieve.setOnClickListener {

                val db = Firebase.firestore
                val user = FirebaseAuth.getInstance().currentUser
                val uid = user?.uid
                val reportRef = ArrayList<String>()


                db.collection("reports").get().addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.get("reportedBy").toString()}")
                        if (document.get("reportedBy").toString() == uid) {
                            reportRef.add(document.id)


                        }
                    }
                }

                location_1.text = reportRef.getOrNull(0).toString()
                location_2.text = reportRef.toString()
            }
        }
    }
}









