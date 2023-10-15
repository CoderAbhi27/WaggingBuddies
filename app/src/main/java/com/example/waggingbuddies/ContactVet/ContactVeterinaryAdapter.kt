package com.example.waggingbuddies.ContactVet

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.waggingbuddies.DataClass.VetsDataCLass
import com.example.waggingbuddies.R
import com.squareup.picasso.Picasso

class ContactVeterinaryAdapter(private val dataList: ArrayList<VetsDataCLass>):
    RecyclerView.Adapter<ContactVeterinaryAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(contactNo : String)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


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

        holder.btnContact.setOnClickListener {
//            val validNumber = Regex("^[+]?[0-9]{10}$")
//            val validNumber2 = Regex("^[+]"+"91"+"[+]?[0-9]{10}$")

//            if (data.contactNo.matches(validNumber
//                ) or data.contactNo.matches(validNumber2)) {

//                val call: Uri = Uri.parse("tel:${data.contactNo}")
//                val intent = Intent(Intent.ACTION_DIAL, call)
//                startActivity(intent)

                mListener.onItemClick(data.contactNo)

//            }
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvName : TextView = view.findViewById(R.id.name)
        val tvEmail : TextView = view.findViewById(R.id.email)
        val tvAddress : TextView = view.findViewById(R.id.adress)
        val tvContact : TextView = view.findViewById(R.id.contact)
        val ivImage : ImageView = view.findViewById(R.id.image)
        val btnContact : Button = view.findViewById(R.id.btnContact)

    }
}