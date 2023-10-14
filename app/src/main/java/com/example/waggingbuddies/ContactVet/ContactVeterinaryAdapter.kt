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
import com.squareup.picasso.Picasso

class ContactVeterinaryAdapter(private val dataList: ArrayList<VetsDataCLass>):
    RecyclerView.Adapter<ContactVeterinaryAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.vet_item_layout, parent, false)
        return ViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]

        holder.tvName.text = data.vetName
        holder.tvContact.text = data.contactNo
        holder.tvAddress.text = data.address
        holder.tvEmail.text = data.contactEmail
        if(data.imgUrl!=null) Picasso.get().load(data.imgUrl).into(holder.ivImage)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
//        val tvQues : TextView = view.findViewById(R.id.Question)
//        val ivQuesImg : ImageView = view.findViewById(R.id.quesImg)
        val tvName : TextView = view.findViewById(R.id.name)
        val tvEmail : TextView = view.findViewById(R.id.email)
        val tvAddress : TextView = view.findViewById(R.id.adress)
        val tvContact : TextView = view.findViewById(R.id.contact)
        val ivImage : ImageView = view.findViewById(R.id.image)

    }
}