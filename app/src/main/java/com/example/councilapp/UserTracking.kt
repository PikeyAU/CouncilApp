package com.example.councilapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.councilapp.databinding.ActivityUserTrackingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.security.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList

class UserTracking : AppCompatActivity() {

    private lateinit var binding: ActivityUserTrackingBinding

    private fun timeToDate(time : String) : LocalDateTime? {
        var time = time.toLong()
        val date = LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault())
        return date
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserTrackingBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                val reportDates = ArrayList<String>()
                val reportStatus = ArrayList<String>()
                val notes = ArrayList<String>()


                db.collection("reports").get().addOnSuccessListener { result ->
                    var counter = 0
                    while(counter < 3){
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.get("reportedBy").toString()}")
                        if (document.get("reportedBy").toString() == uid) {
                            reportRef.add(document.id)
                            reportDates.add(document.get("reportDate").toString())
                            reportStatus.add(document.get("status").toString())
                            notes.add(document.get("notes").toString())
                            counter++

                        }
                    }

                    location_1.text = reportRef[0]
                    location_2.text = reportRef[1]
                    location_3.text = reportRef[2]
                    location_4.text = reportRef[3]
                    date_1.text = timeToDate(reportDates[0].slice(18..27)).toString()
                    date_2.text = timeToDate(reportDates[1].slice(18..27)).toString()
                    date_3.text = timeToDate(reportDates[2].slice(18..27)).toString()
                    date_4.text = timeToDate(reportDates[3].slice(18..27)).toString()
                    status_1.text = reportStatus[0]
                        status_2.text = reportStatus[1]
                        status_3.text = reportStatus[2]
                        status_4.text = reportStatus[3]
                        feed_1.text = notes[0]
                        feed_2.text = notes[1]
                        feed_3.text = notes[2]
                        feed_4.text = notes[3]


                    }
                }
            }

        binding.btnHome.setOnClickListener{
            startActivity(Intent(this@UserTracking, UserReporting::class.java))
        }

        }
    }









