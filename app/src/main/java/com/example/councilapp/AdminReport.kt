package com.example.councilapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.firestore.*

import java.lang.reflect.Member

class AdminReport : AppCompatActivity() {

    private lateinit var data:FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var tempArrayList: ArrayList<User>
    private lateinit var userArrayList:ArrayList<User>
    private lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_admin_report)

        recyclerView=findViewById(R.id.userList)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList= arrayListOf()

        myAdapter= MyAdapter(userArrayList)
        recyclerView.adapter=myAdapter

        eventChangeListener()

    }

    private fun eventChangeListener() {
        data= FirebaseFirestore.getInstance()
        data.collection("reports").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?,
                                 error: FirebaseFirestoreException?)
            {
                if (error!=null){
                    Log.e("Firestore Error",error.message.toString())
                    return
                }
                for (dc:DocumentChange in value?.documentChanges!!){
                    if(dc.type==DocumentChange.Type.ADDED){
                        userArrayList.add(dc.document.toObject(User::class.java))
                    }
                }
                myAdapter.notifyDataSetChanged()
            }

        })

    }


}