package com.example.councilapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.w3c.dom.Text

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btn_register = findViewById<Button>(R.id.btn_register)
        val registerEmail = findViewById<EditText>(R.id.registerEmail)
        val password = findViewById<EditText>(R.id.registerPassword)
        val confirmPassword = findViewById<EditText>(R.id.registerCPassword)
        val username = findViewById<EditText>(R.id.registerUsername)
        val btn_login = findViewById<Button>(R.id.loginBtn)

        btn_register.setOnClickListener {
            when {
                TextUtils.isEmpty(registerEmail.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please Enter Email Address.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(password.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please Enter Password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(confirmPassword.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please Confirm Password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(username.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please Enter a Username.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            else -> {
                val email: String = registerEmail.text.toString().trim { it <= ' '}
                val _password: String = password.text.toString().trim { it <= ' '}
                val _username: String = username.text.toString()

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, _password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        Toast.makeText(
                            this@RegisterActivity,
                            "You Have Been Successfully Registered",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent =
                            Intent(this@RegisterActivity, MainActivity::class.java )
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("user_id", firebaseUser.uid)
                        intent.putExtra("email_id", email)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
            }

        }

        btn_login.setOnClickListener{

            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }
}