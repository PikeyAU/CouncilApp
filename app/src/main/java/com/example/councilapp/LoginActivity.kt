package com.example.councilapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val register_tv = findViewById<TextView>(R.id.register_tv)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val email = findViewById<EditText>(R.id.loginEmail)
        val password = findViewById<EditText>(R.id.loginPassword)


        register_tv.setOnClickListener{

            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        loginBtn.setOnClickListener {
            when {
                TextUtils.isEmpty(email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please Enter Email Address.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please Enter Password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    val email: String = email.text.toString().trim { it <= ' '}
                    val password: String = password.text.toString().trim { it <= ' '}


                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            Toast.makeText(
                                this@LoginActivity,
                                "You Have Been Logged In Successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent =
                                Intent(this@LoginActivity, UserReporting::class.java )
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                            intent.putExtra("email_id", email)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }
            }

        }

        }
    }


