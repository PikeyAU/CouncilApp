package com.example.councilapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import android.provider.MediaStore

class ReportCreatingActivity : AppCompatActivity() {
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creating_report)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            // Ensure all places are visible in the map
            googleMap.setOnMapLoadedCallback {
                val bounds = LatLngBounds.builder()
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 20))
            }
        }

        val reportLocation = findViewById<EditText>(R.id.reportLocation)
        val assetType = findViewById<EditText>(R.id.type)
        val btnImage = findViewById<Button>(R.id.btn_image)
        val note =  findViewById<EditText>(R.id.notes)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)
        val btnCancel = findViewById<Button>(R.id.btn_cancel)


        btnSubmit.setOnClickListener {
            when {
                TextUtils.isEmpty(reportLocation.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@ReportCreatingActivity,
                        "Please enter asset location",
                        Toast.LENGTH_LONG
                    ).show()
                }

                TextUtils.isEmpty(assetType.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@ReportCreatingActivity,
                        "Please enter type of asset here",
                        Toast.LENGTH_LONG
                    ).show()
                }

                TextUtils.isEmpty(note.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@ReportCreatingActivity,
                        "Please enter your notes here",
                        Toast.LENGTH_LONG
                    ).show()
                }


                else -> {

                    Toast.makeText(
                        this@ReportCreatingActivity,
                        "Report Has Been Successfully Added",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        btnImage.setOnClickListener {

        }

        btnCancel.setOnClickListener{
            startActivity(Intent(this@ReportCreatingActivity, ReportCreatingActivity::class.java))
        }
    }
}

