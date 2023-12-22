package com.example.waggingbuddies.AdoptPet

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import com.example.waggingbuddies.Activities.petDescription
import com.example.waggingbuddies.DataClass.PetsDataClass
import com.example.waggingbuddies.DataClass.ShelterDataClass
import com.example.waggingbuddies.R
import com.squareup.picasso.Picasso

class AdoptAPetAdapter(val context: Context): RecyclerView.Adapter<AdoptAPetAdapter.MyViewHolder>() {

    private var PetList = mutableListOf<PetsDataClass>()


    @SuppressLint("NotifyDataSetChanged")
    fun setPetList(petList: MutableLiveData<List<PetsDataClass>?>){
        try {
            this.PetList = petList.value!!.toMutableList()
            Log.d("tag","petList --> ${this.PetList}")
            notifyDataSetChanged()
            Log.d("tag","notified the data set changed")
        }
        catch (e: Exception){
            Log.i("errorInRetrieval","error in retrieval")
        }

    }



    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var petName: TextView =itemView.findViewById(R.id.name)
        var petImage: ImageView =itemView.findViewById(R.id.image)
        var gender: ImageView =itemView.findViewById(R.id.gender)
        var petType: TextView =itemView.findViewById(R.id.petType)
        var petAge: TextView =itemView.findViewById(R.id.age)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj = PetList[position]
        obj.let {
                pet ->
            holder.petName.text = pet.petName
            holder.petType.text = pet.petType
            holder.petAge.text= "${pet.petAge} YEARS"
            if(pet.gender){
                holder.gender.setImageResource(R.drawable.baseline_female_24)
            } else {
                holder.gender.setImageResource(R.drawable.baseline_male_24)
            }

            if(pet.petImageURL!=null){
                Picasso.get().load(pet.petImageURL).into(holder.petImage)
//                val requestOptions = RequestOptions()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL) // Caching strategy
//                    .placeholder(R.drawable.placeholder_image) // Placeholder image
//                    .error(R.drawable.error_image) // Error image

// Load the image into the ImageView
//                Glide.with(this)
//                    .load(pet.petImageURL)
//                 // Optional RequestOptions
//                    .into(holder.petImage)
//                Log.i("url",pet.petImageURL)
           }

            holder.itemView.setOnClickListener {
                // When a pet card is clicked, open PetDescriptionActivity and pass the pet data
                val intent = Intent(context, petDescription::class.java)
                intent.putExtra("petName", pet.petName)
                intent.putExtra("petType", pet.petType)
                intent.putExtra("petAge", pet.petAge)
                intent.putExtra("petImageUrl", pet.petImageURL)
                intent.putExtra("petBreed", pet.petBreed)
                intent.putExtra("petHealth", pet.petHealth)
                intent.putExtra("petOwner", pet.petOwnersName)
                intent.putExtra("petAddress", pet.petAddress)
                intent.putExtra("adoptionMsg", pet.adoptionMsg)
                intent.putExtra("gender", pet.gender)
                intent.putExtra("email", pet.petOwnerEmail)
                intent.putExtra("time", pet.petAdoptionTime)
                intent.putExtra("id", pet.petID)
                context.startActivity(intent)

            }


        }
    }

    override fun getItemCount(): Int {
        return PetList.size
    }


}
