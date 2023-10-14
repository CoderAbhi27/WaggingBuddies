package com.example.waggingbuddies.ContactVet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.waggingbuddies.DataClass.VetsDataCLass
import com.example.waggingbuddies.R

class ContactVeterinaryAdapter(private val dataList: ArrayList<VetsDataCLass>):
    RecyclerView.Adapter<ContactVeterinaryAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.vet_item_layout, parent, false)
        return ViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
//        val tvQues : TextView = view.findViewById(R.id.Question)
//        val ivQuesImg : ImageView = view.findViewById(R.id.quesImg)



    }
}