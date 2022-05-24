package com.example.councilapp

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class gridviewadapter (var context: Context,var arrayList: ArrayList<gridviewitem>):BaseAdapter(){
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Any {
       return arrayList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        var view:View=View.inflate(context,R.layout.grid_item_list,null)
        var names: TextView= view.findViewById(R.id.grid_text)

        var Griditem: gridviewitem= arrayList.get(position)
        names.text= Griditem.name
        return view
    }
}