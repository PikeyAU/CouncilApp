package com.example.councilapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = FirebaseAuth.getInstance()

        if (user.currentUser !=null) {
            user.currenUser?.Let {

                binding.tvUserEmail.text = it.email


            }
        }

        binding.btnSignout.SetOnClickLister {
            user.signOut()
            startActivity(
                Intent(
                    packageContext:this,

                )
            )
        }

    }
}

