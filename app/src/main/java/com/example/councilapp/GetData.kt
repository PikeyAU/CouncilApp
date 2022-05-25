package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.councilapp.databinding.ActivityGetDataBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.jar.Attributes

class GetData : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityGetDataBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val typeName: String = binding.typeName.query.toString();
            if (typeName.isNotEmpty()) {

                getData(typeName)
            } else {
                Toast.makeText(
                    this, "please enter the Name", Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun getData(typeName: String) {
        database = FirebaseDatabase.getInstance().getReference("Cases")
        database.child(typeName).get().addOnSuccessListener {
            if (it.exists()) {
                val name=it.child("name").value
                val address=it.child("address").value
                val location=it.child("location").value
                val assets=it.child("assets").value
                val time=it.child("time").value
                val notes=it.child("notes").value
                Toast.makeText(
                    this, "get data successfully", Toast.LENGTH_LONG
                ).show()
                binding.txtName.text=name.toString();
                binding.txtAddress.text=address.toString();
                binding.txtLocation.text=location.toString();
                binding.txtAssets.text=assets.toString();
                binding.txtTime.text=time.toString();
                binding.txtNotes.text=notes.toString();

            } else {
                Toast.makeText(
                    this, "the name does not exist", Toast.LENGTH_LONG
                ).show()


            }
        }.addOnFailureListener{
            Toast.makeText(this,"failed",Toast.
            LENGTH_LONG).show()
        }
    }
}