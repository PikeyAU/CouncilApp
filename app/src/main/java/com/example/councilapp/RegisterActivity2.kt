package com.example.councilapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.councilapp.repository.Residents
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        val db = Firebase.firestore
        val continueBtn = findViewById<Button>(R.id.continueBtn)
        val registerfullname = findViewById<EditText>(R.id.registerfullname)
        val registerage = findViewById<EditText>(R.id.registerAge)
        val registeraddress = findViewById<EditText>(R.id.registerAddress)
        val registercity = findViewById<EditText>(R.id.registerCity)
        val registerstate = findViewById<EditText>(R.id.registerState)
        val registerpostcode = findViewById<EditText>(R.id.registerPostcode)
        val registerphone = findViewById<EditText>(R.id.registerPhone)
        val upattern = ("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]\$".toRegex())
        val namepattern = ("^[A-Za-z]+((\\s)?((\\'|\\-|\\.)?([A-Za-z])+))*\$".toRegex())
        val alphapattern = ("^[a-zA-Z]+\$".toRegex())
        val numpattern = ("^100|[1-9]?\\d\$".toRegex())
        val addresspattern = ("^[#.0-9a-zA-Z\\s,-\\/]+(Avenue|Lane|Road|Boulevard|Drive|Street|Ave|Dr|Rd|Blvd|Ln|St)\$".toRegex())
        val dobpattern = ("^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]\$".toRegex())

        continueBtn.setOnClickListener {
            when {
                TextUtils.isEmpty(registerfullname.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Please Enter Your Name.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                !(namepattern.containsMatchIn(registerfullname.text.toString())) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Name can only have alphanumeric characters and hyphens",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(registerage.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Please Enter Date of Birth.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                !(dobpattern.containsMatchIn(registerage.text.toString())) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Please use correct date format",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(registerphone.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Please Enter a Phone Number.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                !(numpattern.containsMatchIn(registerphone.text.toString())) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Phone number can only be in numbers.",
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

                !(addresspattern.containsMatchIn(registeraddress.text.toString())) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Make sure your address type starts with Upper case.",
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

                !(alphapattern.containsMatchIn(registercity.text.toString())) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "City can only be in alphabets.",
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

                !(alphapattern.containsMatchIn(registerstate.text.toString())) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "State can only be in alphabets.",
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

                !(numpattern.containsMatchIn(registerpostcode.text.toString())) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Postcode can only be in numbers.",
                        Toast.LENGTH_SHORT
                    ).show()
                }


                else -> {
                    val fullname: String = registerfullname.text.toString().trim()
                    val age: String = registerage.text.toString().trim()
                    val address: String = registeraddress.text.toString().trim()
                    val city: String = registercity.text.toString().trim()
                    val state: String = registerstate.text.toString().trim()
                    val postcode: String = registerpostcode.text.toString().trim()
                    val phoneNumber: String = registerphone.text.toString().trim()
                    val userid : String = intent.getStringExtra("user_id").toString()
                    val emailAddress : String = intent.getStringExtra("email_id").toString()

                    val user = hashMapOf<String, Any>(
                        "userid" to userid,
                        "fullname" to fullname,
                        "date of birth" to age,
                        "address" to address,
                        "city" to city,
                        "state" to state,
                        "postcode" to postcode,
                        "phone number" to phoneNumber,
                        "emailAddress" to emailAddress

                    )
                    Residents.addResident(userid, fullname, age, phoneNumber, address, city, state, postcode, emailAddress)
                    val intent = Intent(this@RegisterActivity2, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}