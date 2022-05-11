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

class RegisterActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        val btn_register2 = findViewById<Button>(R.id.btn_register2)
        val registerfullname = findViewById<EditText>(R.id.registerfullname)
        val registerage = findViewById<EditText>(R.id.registerAge)
        val registeraddress = findViewById<EditText>(R.id.registerAddress)
        val registercity = findViewById<EditText>(R.id.registerCity)
        val registerstate = findViewById<EditText>(R.id.registerState)
        val registerpostcode = findViewById<EditText>(R.id.registerPostcode)
        val btn_login = findViewById<Button>(R.id.loginBtn)

        btn_register2.setOnClickListener {
            when {
                TextUtils.isEmpty(registerfullname.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Please Enter Your Name.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(registerage.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Please Enter Age.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(registeraddress.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Please Enter Your Address.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(registercity.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Please Enter a City.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(registerstate.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Please Enter a State.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(registerpostcode.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Please Enter a Post Code.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    val fullname: String = registerfullname.text.toString().trim()
                    val age: String = registerage.text.toString()
                    val address: String = registeraddress.text.toString()
                    val city: String = registercity.text.toString()
                    val state: String = registerstate.text.toString()
                    val postcode: String = registerpostcode.text.toString()

                    data class User(
                        var fullname : String? = null,
                        var age : String? = null,
                        var address : String? = null,
                        var city : String? = null,
                        var state : String? = null,
                        var postcode : String = null
                    )

                    val user = User(fullname, age, address, city, state, postcode)
                    val rootRef = FirebaseFirestore.getInstance()
                    val usersRef = rootRef.collection("users")
                    usersRef.document(fullname).set(user).addOnCompleteListener(fullname, age, address, city, state, postcode)
                    
                }
            }
        }

        btn_login.setOnClickListener{

            startActivity(Intent(this@RegisterActivity2, LoginActivity::class.java))
        }
    }
}