package com.example.waggingbuddies.AdoptPet

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.waggingbuddies.DataClass.PetsDataClass
import com.example.waggingbuddies.DataClass.ShelterDataClass
import com.example.waggingbuddies.R
import com.squareup.picasso.Picasso

class AdoptAPetAdapter: RecyclerView.Adapter<AdoptAPetAdapter.MyViewHolder>() {

    private var petList = mutableListOf<PetsDataClass>()


    /*@SuppressLint("NotifyDataSetChanged")
    fun seteventList(petList: MutableLiveData<List<PetsDataClass>?>){
        try {
            this.PetList = petList.value!!.toMutableList()
            Log.d("tag","eventList --> ${this.PetList}")
            notifyDataSetChanged()
            Log.d("tag","notified the data set changed")
        }
        catch (e: Exception){
            Log.i("errorInRetrieval","error in retrieval")
        }

    }*/



    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var petName: TextView =itemView.findViewById(R.id.name)
        var petImage: ImageView =itemView.findViewById(R.id.image)
        var isMale: ImageView =itemView.findViewById(R.id.male)
        var isFemale: ImageView =itemView.findViewById(R.id.female)
        var petType: TextView =itemView.findViewById(R.id.petType)
        var petAge: TextView =itemView.findViewById(R.id.age)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj = petList[position]
        obj.let {
                pet ->
            holder.petName.text = pet.petName
            holder.petType.text = pet.petType
            holder.petAge.text= "${pet.petAge} YEARS"
            if(pet.gender){
                holder.isFemale.visibility = View.VISIBLE
                holder.isMale.visibility = View.GONE
            } else {
                holder.isMale.visibility = View.VISIBLE
                holder.isFemale.visibility = View.GONE
            }

            if(pet.petImageURL!=null){
                Picasso.get().load(pet.petImageURL).into(holder.petImage)
            }



        }
    }

    override fun getItemCount(): Int {
        return petList.size
    }


}
