package com.example.councilapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import com.example.councilapp.repository.Reports
import com.example.myapplication.GetData

class admin_dash : AppCompatActivity() {
    private var gridView: GridView?=null
    private var arrayList:ArrayList<gridviewitem>? =null
    private var gridadapter: gridviewadapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dash)

        var reportBtn = findViewById<Button>(R.id.reportBtn)
        val fullName = intent.getStringExtra("user_name")

        var userName = findViewById<TextView>(R.id.welcometext)
        gridView = findViewById(R.id.data_widgets)
        arrayList = ArrayList()
        arrayList = setDataList()
        gridadapter = gridviewadapter(applicationContext , arrayList!!)
        gridView?.adapter = gridadapter

        userName.text = "$fullName"


        reportBtn.setOnClickListener {
            startActivity(Intent(this@admin_dash, GetData::class.java))
        }
    }
    private fun setDataList(): ArrayList<gridviewitem>? {

        arrayList?.add(gridviewitem("Number of New reports"))
        arrayList?.add(gridviewitem("Number of Reports in last 24 hours"))
        arrayList?.add(gridviewitem("Number of Reports in last 7 days"))
        arrayList?.add(gridviewitem("Number of Active Reports"))
        arrayList?.add(gridviewitem("Number of Solved Reports in last 7 days"))


        return arrayList
    }
}



