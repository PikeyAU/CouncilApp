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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.util.Patterns

class RegisterActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        val db = Firebase.firestore
        val continueBtn = findViewById<Button>(R.id.continueBtn)
        val btn_back = findViewById<Button>(R.id.btn_back)
        val registerfullname = findViewById<EditText>(R.id.registerfullname)
        val registerage = findViewById<EditText>(R.id.registerAge)
        val registeraddress = findViewById<EditText>(R.id.registerAddress)
        val registercity = findViewById<EditText>(R.id.registerCity)
        val registerstate = findViewById<EditText>(R.id.registerState)
        val registerpostcode = findViewById<EditText>(R.id.registerPostcode)
        val registerphone = findViewById<EditText>(R.id.registerPhone)
<<<<<<< Updated upstream
        val namepattern = Regex("/^[a-z ,.'-]+\$/i")
        val alphapattern = Regex("/^[A-Za-z]+\$/")
        val numpattern = Regex("/^100|[1-9]?\\d\$/")
        val addresspattern = Regex("/^[#.0-9a-zA-Z\\s,-\\/]+(Avenue|Lane|Road|Boulevard|Drive|Street|Ave|Dr|Rd|Blvd|Ln|St)\$/gm")

=======
        //var namepattern = ("/^[a-zA-Z._-]+\$/".toRegex())
        var alphapattern = ("/^[A-Za-z]+\$/".toRegex())
        var numpattern = ("/^100|[1-9]?\\d\$/".toRegex())
        var addresspattern = ("/^[#.0-9a-zA-Z\\s,-\\/]+(Avenue|Lane|Road|Boulevard|Drive|Street|Ave|Dr|Rd|Blvd|Ln|St)\$/gm".toRegex())
>>>>>>> Stashed changes

        continueBtn.setOnClickListener {
            when {
                //Fullname
                TextUtils.isEmpty(registerfullname.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Please Enter Your Name.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

<<<<<<< Updated upstream
                !(registerfullname.text.toString()).matches(namepattern) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Name can only have alphanumeric characters and hyphens",
                        Toast.LENGTH_SHORT
                    ).show()
                }
=======
                //!(namepattern.containsMatchIn(registerfullname.text.toString())) -> {
                //    Toast.makeText(
                //        this@RegisterActivity2,
                //        "Name can only have alphanumeric characters and hyphens",
               //         Toast.LENGTH_SHORT
               //     ).show()
               // }
>>>>>>> Stashed changes

                //Age
                TextUtils.isEmpty(registerage.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Please Enter Data of Birth.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

<<<<<<< Updated upstream
                !(registerage.text.toString()).matches(numpattern) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Age can only be a number",
                        Toast.LENGTH_SHORT
                    ).show()
                }

=======
>>>>>>> Stashed changes
                //Address
                TextUtils.isEmpty(registeraddress.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Please Enter Your Address.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

<<<<<<< Updated upstream
                !(registeraddress.text.toString()).matches(addresspattern) -> {
=======
                !(addresspattern.containsMatchIn(registeraddress.text.toString())) -> {
>>>>>>> Stashed changes
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Please check your address and include either Ave, Dr, Rd, Blvd, Ln, or St",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                //City
                TextUtils.isEmpty(registercity.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Please Enter a City.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

<<<<<<< Updated upstream
                !(registercity.text.toString()).matches(alphapattern) -> {
=======
                !(alphapattern.containsMatchIn(registercity.text.toString())) -> {
>>>>>>> Stashed changes
                    Toast.makeText(
                        this@RegisterActivity2,
                        "City can only be in alphabets",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                //State
                TextUtils.isEmpty(registerstate.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Please Enter a State.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

<<<<<<< Updated upstream
                !(registerstate.text.toString()).matches(alphapattern) -> {
=======
                !(alphapattern.containsMatchIn(registerstate.text.toString())) -> {
>>>>>>> Stashed changes
                    Toast.makeText(
                        this@RegisterActivity2,
                        "State can only be in alphabets",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                //Post code
                TextUtils.isEmpty(registerpostcode.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Please Enter a Post Code.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

<<<<<<< Updated upstream
                !(registerpostcode.text.toString()).matches(numpattern) -> {
=======
                !(numpattern.containsMatchIn(registerpostcode.text.toString())) -> {
>>>>>>> Stashed changes
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Post code can only be a number",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                //Phone number
                TextUtils.isEmpty(registerphone.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Please Enter a Phone Number.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

<<<<<<< Updated upstream
                !(registerphone.text.toString()).matches(numpattern) -> {
=======
                !(numpattern.containsMatchIn(registerphone.text.toString())) -> {
>>>>>>> Stashed changes
                    Toast.makeText(
                        this@RegisterActivity2,
                        "Phone number can only be a number",
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

                    val user = hashMapOf<String, Any>(
                        "userid" to userid,
                        "fullname" to fullname,
                        "date of birth" to age,
                        "address" to address,
                        "city" to city,
                        "state" to state,
                        "postcode" to postcode,
                        "phone number" to phoneNumber

                    )

                    val fireStoreDatabase = FirebaseFirestore.getInstance()

                    fireStoreDatabase.collection("users")
                        .add(user)
                        .addOnSuccessListener{
                            Log.d(TAG, "Document added with ID ${it.id}")
                            val intent =
                                Intent(this@RegisterActivity2, MainActivity::class.java )
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("fullName", fullname)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener { exception ->
                            Log.w(TAG, "Error adding document $exception")
                            Toast.makeText(
                                this@RegisterActivity2,
                                exception.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                }
            }
        }

        btn_back.setOnClickListener{

            startActivity(Intent(this@RegisterActivity2, RegisterActivity::class.java))
        }
    }
}