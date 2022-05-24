package com.example.councilapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import com.example.councilapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        user = FirebaseAuth.getInstance()

        if (user.currentUser !=null) {
            user.currentUser?.let {

                binding.tvFullname.text = it.email


            }
        }

        binding.logoutBtn.setOnClickListener {
            user.signOut()
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
            finish()
        }



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