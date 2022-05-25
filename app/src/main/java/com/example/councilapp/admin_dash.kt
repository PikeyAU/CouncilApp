package com.example.councilapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView

class admin_dash : AppCompatActivity() {
    private var gridView: GridView?=null
    private var arrayList:ArrayList<gridviewitem>? =null
    private var gridadapter: gridviewadapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dash)

        gridView = findViewById(R.id.data_widgets)
        arrayList = ArrayList()
        arrayList = setDataList()
        gridadapter = gridviewadapter(applicationContext , arrayList!!)
        gridView?.adapter = gridadapter

    }
    private fun setDataList(): ArrayList<gridviewitem>? {

        arrayList?.add(gridviewitem("Number of views in last 24 hours"))
        arrayList?.add(gridviewitem("Number of views in last 7 days"))
        arrayList?.add(gridviewitem("Number of reports in last 24 hours"))
        arrayList?.add(gridviewitem("Number of reports in last 7 days"))
        arrayList?.add(gridviewitem("Number of active reports"))
        arrayList?.add(gridviewitem("Number of solved reports in last 7 days"))


        return arrayList
    }
}



