package com.example.councilapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.councilapp.model.Admin
import com.example.councilapp.repository.Admins
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val register_tv = findViewById<TextView>(R.id.register_tv)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val adminBtn = findViewById<Button>(R.id.adminBtn)
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

        adminBtn.setOnClickListener{
            val email: String = email.text.toString().trim { it <= ' '}
            val password: String = password.text.toString().trim { it <= ' '}

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val uid = FirebaseAuth.getInstance().currentUser!!.uid

                    Admins.getAdmin(
                        uid = uid, /* You should use the actual uid of an admin. */
                        //wipFun = {}, // Optional.
                        //failFun = {}, // Optional.
                        //doneFun = {}, // Optional.
                    ) { admin ->
                        Log.i(TAG, "getAdminExample: Obtained admin $admin")

                        if (admin.uid == uid){
                            Toast.makeText(
                                this@LoginActivity,
                                "You Have Been Logged In Successfully as Admin",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent =
                                Intent(this@LoginActivity, admin_dash::class.java )
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                            intent.putExtra("email_id", email)
                            intent.putExtra("user_name", admin.fullName)
                            startActivity(intent)
                            finish()
                        } else {
                            val intent =
                                Intent(this@LoginActivity, LoginActivity::class.java )
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()

                            Toast.makeText(
                                this@LoginActivity,
                                "You are not an admin",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        }
                    }


        }

        }
    }
}


