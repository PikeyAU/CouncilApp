package com.example.councilapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fullName = intent.getStringExtra("fullname")

        val tv_fullname = findViewById<TextView>(R.id.tv_fullname)
        val logoutBtn = findViewById<Button>(R.id.logoutBtn)

        tv_fullname.text = "Welcome : $fullName"

        logoutBtn.setOnClickListener{
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }


    }
}

//Experimental commit (new branch)