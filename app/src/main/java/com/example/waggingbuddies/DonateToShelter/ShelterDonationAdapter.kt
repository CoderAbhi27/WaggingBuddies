package com.example.waggingbuddies.DonateToShelter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.waggingbuddies.DataClass.ShelterDataClass
import com.example.waggingbuddies.R

class ShelterDonationAdapter: RecyclerView.Adapter<ShelterDonationAdapter.MyViewHolder>() {

        private var ShelterList = mutableListOf<ShelterDataClass>()


        @SuppressLint("NotifyDataSetChanged")
        fun seteventList(shelterList: MutableLiveData<List<ShelterDataClass>?>){
            try {
                this.ShelterList = shelterList.value!!.toMutableList()
                Log.d("tag","eventList --> ${this.ShelterList}")
                notifyDataSetChanged()
                Log.d("tag","notified the data set changed")
            }
            catch (e: Exception){
                Log.i("errorInRetrieval","error in retrieval")
            }

        }



        class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            var shelterName: TextView =itemView.findViewById(R.id.textShelterName)
            var shelterLocation: TextView =itemView.findViewById(R.id.textShelterLocation)
            var dogCapacity: TextView =itemView.findViewById(R.id.dogCapacity)
            var dogStrength: TextView =itemView.findViewById(R.id.dogStrength)
            var catCapacity: TextView =itemView.findViewById(R.id.catsCapacity)
            var catStrenth: TextView =itemView.findViewById(R.id.catsStrength)
            var cowCapacity: TextView =itemView.findViewById(R.id.cowsCapacity)
            var cowStrenth: TextView =itemView.findViewById(R.id.cowsStrength)
            var birdCapacity: TextView =itemView.findViewById(R.id.birdsCapacity)
            var birdStrenth: TextView =itemView.findViewById(R.id.textShelterLocation)
            var dogLayout: LinearLayout =itemView.findViewById(R.id.DogDetails)
            var catLayout: LinearLayout =itemView.findViewById(R.id.CatDetails)
            var cowLayout: LinearLayout =itemView.findViewById(R.id.CowDetails)
            var birdLayout: LinearLayout =itemView.findViewById(R.id.BirdDetails)
            var btnDonate : Button = itemView.findViewById(R.id.btnDonate)
            var totalDonation : TextView = itemView.findViewById(R.id.textTotalDonations)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.shelter_card, parent, false)
            return MyViewHolder(view)

        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val obj = ShelterList[position]
            obj.let {
                    shelter ->
holder.shelterName.text = shelter.name
                holder.shelterLocation.text = shelter.address

                val stages : List<Int> = shelter.totalCapacity
                val str : List<Int> = shelter.currentStrength
                if (stages[0] == 0)
                    holder.dogLayout.visibility = View.GONE
                if (stages[1] == 0)
                    holder.catLayout.visibility = View.GONE
                if (stages[2] == 0)
                    holder.cowLayout.visibility = View.GONE
                if (stages[3] == 0)
                    holder.birdLayout.visibility = View.GONE

holder.totalDonation.text = "Donation: ${shelter.donationsRecieved}"
                holder.dogCapacity.text= "Capacity: ${stages[0]}"
                holder.dogStrength.text="Strength: ${str[0]}"
                holder.catCapacity.text= "Capacity: ${stages[1]}"
                holder.catStrenth.text="Strength: ${str[1]}"
                holder.cowCapacity.text= "Capacity: ${stages[2]}"
                holder.cowStrenth.text="Strength: ${str[2]}"
                holder.birdCapacity.text= "Capacity: ${stages[3]}"
                holder.birdStrenth.text="Strength: ${str[3]}"




            }
        }

        override fun getItemCount(): Int {
            return ShelterList.size
        }


    }
