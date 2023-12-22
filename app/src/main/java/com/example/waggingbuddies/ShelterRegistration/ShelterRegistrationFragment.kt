package com.example.waggingbuddies.ShelterRegistration

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.waggingbuddies.DataClass.ShelterDataClass
import com.example.waggingbuddies.DonateToShelter.retrofit.RetrofitInstanceShelter
import com.example.waggingbuddies.R
import com.example.waggingbuddies.ShelterRegistration.retrofit.ApiResponse
import com.example.waggingbuddies.ShelterRegistration.retrofit.RetrofitInstancePostShelter
import com.example.waggingbuddies.databinding.FragmentShelterRegistrationBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response


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
                val capacityList = arrayListOf<Int>(0,0,0,0)
                val currList = arrayListOf<Int>(0,0,0,0)
//                for(i in 0..3) {capacityList[i]=0; currList[i]=0;}
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
                    if(binding.editCurrentCats.text.isNotEmpty()) currList[1] = binding.editCurrentCats.text.toString().toInt()
                }

                if(binding.checkCows.isChecked){
                    if(binding.editCapacityCows.text.isEmpty()) {
                        Toast.makeText(context, "Please Enter the Shelter address", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    capacityList[2]= binding.editCapacityCows.text.toString().toInt()
                    if(binding.editCurrentCows.text.isNotEmpty()) currList[2] = binding.editCurrentCows.text.toString().toInt()
                }

                if(binding.checkBirds.isChecked){
                    if(binding.editCapacityBirds.text.isEmpty()) {
                        Toast.makeText(context, "Please Enter the Shelter address", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    capacityList[3]= binding.editCapacityBirds.text.toString().toInt()
                    if(binding.editCurrentBirds.text.isNotEmpty()) currList[3] = binding.editCurrentBirds.text.toString().toInt()
                }

                data = ShelterDataClass(
                    binding.editShelterName.text.toString(),
                    binding.editShelterLocation.text.toString(),
                    capacityList.toList(),
                    currList.toList(),
                    1
                )

                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Terms and Conditions")
                builder.setMessage(getString(R.string.tnc_shelter))
//                builder.setMessage("ahschsansna")
                builder.setPositiveButton("ACCEPT", DialogInterface.OnClickListener{ dialog, which->
                    pushData(data)
                })
                builder.setNegativeButton("Cancel", DialogInterface.OnClickListener{ dialog, which->

                })
                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()


            }



        }
    }

    private fun submit() {
        TODO("Not yet implemented")
    }


    fun pushData(data: ShelterDataClass) {


        Log.i("dataModel",data.toString())
        val call = RetrofitInstancePostShelter().shelterPostApiService.uplaodData(data)
        call.enqueue(object : retrofit2.Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {



                Log.i("Tag", response.toString())

                Log.i("response",response.body().toString())

                if (response.body() == null) {
                    Toast.makeText(
                        requireContext(),
                        "Something went wrong!" + response.code().toString(),
                        Toast.LENGTH_SHORT
                    ).show()



                }
                else
                    Toast.makeText(
                        requireContext(),
                        "Shelter Registered Successfully",
                        Toast.LENGTH_SHORT
                    ).show()


            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.i("Tag",t.toString())

                Toast.makeText(requireContext(),"Try again !!, It may happen first time",Toast.LENGTH_SHORT).show()
            }
        })
    }
}