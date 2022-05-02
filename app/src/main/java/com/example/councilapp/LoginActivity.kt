package com.example.councilapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.councilapp.databinding.ActivityMainBinding
import com.example.practice_project.databinding.ActivityMainBinding


class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            binding.button.text = "login"
        }
    }
}

/*
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//            setContentView(R.layout.activity_login)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button.text = "login"
    }
}
 */