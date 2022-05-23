package com.example.councilapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btn_register = findViewById<Button>(R.id.btn_register)
        val registerEmail = findViewById<EditText>(R.id.registerAge)
        val password = findViewById<EditText>(R.id.registerPassword)
        val confirmPassword = findViewById<EditText>(R.id.registerCPassword)
        val username = findViewById<EditText>(R.id.registerUsername)
        val btn_login = findViewById<Button>(R.id.loginBtn)
        var upattern = Regex("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]\$")

        btn_register.setOnClickListener {
            when {
                //Email
                TextUtils.isEmpty(registerEmail.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please Enter Email Address.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                !Patterns.EMAIL_ADDRESS.matcher(registerEmail.text.toString()).matches() -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please Enter Valid Email Address.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                //Password and Confirm password
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

                (password.text.toString()) != (confirmPassword.text.toString()) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Password Does Not Match.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                //Username
                TextUtils.isEmpty(username.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please Enter a Username.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                !(username.text.toString()).matches(upattern) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Username must only consist of :" +
                                "lower and upper alphanumeric characters," +
                                "optional (.),(_), and (-) characters," +
                                "optional characters cannot be the first or last character," +
                                "between 5 to 20 characters.",
                        Toast.LENGTH_LONG
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
                            Intent(this@RegisterActivity, RegisterActivity2::class.java )
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