package com.example.councilapp
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val userList:ArrayList<User>): RecyclerView.Adapter<MyAdapter.MyViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {

        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.item,
            parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {

        val user:User=userList[position]

        holder.reportDate.text=user.reportDate
        holder.asset.text=user.assetType
        holder.location.text=user.location
        holder.notes.text=user.notes
        holder.reportBy.text=user.reportBy
        holder.status.text=user.status
    }

    override fun getItemCount(): Int {
        return userList.size
    }

   public  class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val asset: TextView=itemView.findViewById(R.id.inputAssetType)

        val reportBy: TextView=itemView.findViewById(R.id.inputReportedBy)

        val reportDate: TextView=itemView.findViewById(R.id.inputReportDate)

        val notes: TextView=itemView.findViewById(R.id.inputNotes)

        val status: TextView=itemView.findViewById(R.id.inputStatus)

        val location: TextView=itemView.findViewById(R.id.inputLocation)

}





}