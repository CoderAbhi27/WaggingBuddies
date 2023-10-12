package com.example.waggingbuddies.ShelterRegistration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.waggingbuddies.DataClass.ShelterDataClass
import com.example.waggingbuddies.R
import com.example.waggingbuddies.databinding.FragmentShelterRegistrationBinding


class ShelterRegistrationFragment : Fragment(R.layout.fragment_shelter_registration) {

    private lateinit var binding : FragmentShelterRegistrationBinding
    private lateinit var data: ShelterDataClass
    private var checked = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShelterRegistrationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.checkDogs.setOnClickListener {
            if(binding.checkDogs.isChecked){
                if(checked==0) binding.tvCapacityHeading.visibility = View.VISIBLE
                checked++
                binding.dogs.visibility = View.VISIBLE
            } else {
                if(checked==1) binding.tvCapacityHeading.visibility = View.GONE
                checked--
                binding.dogs.visibility = View.GONE
            }

        }

        binding.checkCats.setOnClickListener {
            if(binding.checkCats.isChecked){
                if(checked==0) binding.tvCapacityHeading.visibility = View.VISIBLE
                checked++
                binding.cats.visibility = View.VISIBLE
            } else {
                if(checked==1) binding.tvCapacityHeading.visibility = View.GONE
                checked--
                binding.cats.visibility = View.GONE
            }

        }

        binding.checkCows.setOnClickListener {
            if(binding.checkCows.isChecked){
                if(checked==0) binding.tvCapacityHeading.visibility = View.VISIBLE
                checked++
                binding.cows.visibility = View.VISIBLE
            } else {
                if(checked==1) binding.tvCapacityHeading.visibility = View.GONE
                checked--
                binding.cows.visibility = View.GONE
            }

        }

        binding.checkBirds.setOnClickListener {
            if(binding.checkBirds.isChecked){
                if(checked==0) binding.tvCapacityHeading.visibility = View.VISIBLE
                checked++
                binding.birds.visibility = View.VISIBLE
            } else {
                if(checked==1) binding.tvCapacityHeading.visibility = View.GONE
                checked--
                binding.birds.visibility = View.GONE
            }

        }

        binding.submitBtn.setOnClickListener {
            if(binding.editShelterName.text.isEmpty()){
                Toast.makeText(context, "Please Enter the Shelter name", Toast.LENGTH_SHORT).show()
            }
            else if(binding.editShelterLocation.text.isEmpty()){
                Toast.makeText(context, "Please Enter the Shelter address", Toast.LENGTH_SHORT).show()
            }
            else if(checked==0){
                Toast.makeText(context, "Please select at least 1 pet category", Toast.LENGTH_SHORT).show()
            }
            else{
                val capacityList = arrayListOf<Int>(4)
                val currList = arrayListOf<Int>(4)
                for(i in 0..4) {capacityList[i]=0; currList[i]=0;}
                if(binding.checkDogs.isChecked){
                    if(binding.editCapacityDogs.text.isEmpty()) {
                        Toast.makeText(context, "Please Enter the Shelter address", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    capacityList[0]= binding.editCapacityDogs.text.toString().toInt()
                    if(binding.editCurrentDogs.text.isNotEmpty()) currList[0] = binding.editCurrentDogs.text.toString().toInt()
                }

                if(binding.checkCats.isChecked){
                    if(binding.editCapacityCats.text.isEmpty()) {
                        Toast.makeText(context, "Please Enter the Shelter address", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    capacityList[1]= binding.editCapacityCats.text.toString().toInt()
                    if(binding.editCurrentCats.text.isNotEmpty()) currList[0] = binding.editCurrentCats.text.toString().toInt()
                }

                if(binding.checkCows.isChecked){
                    if(binding.editCapacityCows.text.isEmpty()) {
                        Toast.makeText(context, "Please Enter the Shelter address", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    capacityList[2]= binding.editCapacityCows.text.toString().toInt()
                    if(binding.editCurrentCows.text.isNotEmpty()) currList[0] = binding.editCurrentCows.text.toString().toInt()
                }

                if(binding.checkBirds.isChecked){
                    if(binding.editCapacityBirds.text.isEmpty()) {
                        Toast.makeText(context, "Please Enter the Shelter address", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    capacityList[3]= binding.editCapacityBirds.text.toString().toInt()
                    if(binding.editCurrentBirds.text.isNotEmpty()) currList[0] = binding.editCurrentBirds.text.toString().toInt()
                }

                data = ShelterDataClass(
                    binding.editShelterName.text.toString(),
                    binding.editShelterLocation.text.toString(),
                    capacityList.toList(),
                    currList.toList(),
                    0
                )

                pushData(data)


            }



        }
    }


    private fun pushData(data: ShelterDataClass) {

    }
}