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

        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")

        val tv_user_id = findViewById<TextView>(R.id.tv_user_id)
        val tv_email_id = findViewById<TextView>(R.id.tv_email_id)
        val logoutBtn = findViewById<Button>(R.id.logoutBtn)

        tv_user_id.text = "User ID : $userId"
        tv_email_id.text = "Email Address: $emailId"

        logoutBtn.setOnClickListener{
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }


    }
}