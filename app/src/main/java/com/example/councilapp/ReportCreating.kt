package com.example.councilapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.lang.Override as Override

class ReportCreating : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_creating)

        val reporterName = findViewById<EditText>(R.id.reporterName)
        val reporterAddress = findViewById<EditText>(R.id.reporterAddress)
        val reportLocation = findViewById<EditText>(R.id.reportLocation)
        val assetType = findViewById<EditText>(R.id.type)
        val btnImage = findViewById<Button>(R.id.btn_image)
        val note =  findViewById<EditText>(R.id.notes)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)
        val btnCancel = findViewById<Button>(R.id.btn_cancel)

        btnSubmit.setOnClickListener {
            when {
                TextUtils.isEmpty(reporterName.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@ReportCreating,
                        "Please enter your name here",
                        Toast.LENGTH_LONG
                    ).show()
                }

                TextUtils.isEmpty(reporterAddress.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@ReportCreating,
                        "Please enter your address here",
                        Toast.LENGTH_LONG
                    ).show()
                }

                TextUtils.isEmpty(reportLocation.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@ReportCreating,
                        "Please enter asset location",
                        Toast.LENGTH_LONG
                    ).show()
                }

                TextUtils.isEmpty(assetType.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@ReportCreating,
                        "Please enter type of asset here",
                        Toast.LENGTH_LONG
                    ).show()
                }

                TextUtils.isEmpty(note.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@ReportCreating,
                        "Please enter your notes here",
                        Toast.LENGTH_LONG
                    ).show()
                }


            else -> {

                Toast.makeText(
                    this@ReportCreating,
                    "You Have Been Successfully Registered",
                    Toast.LENGTH_LONG
                ).show()
                }
            }
        }

        btnImage.setOnClickListener {
            //
        }

        btnCancel.setOnClickListener{
            startActivity(Intent(this@ReportCreating, ReportCreating::class.java))
        }
    }
}

