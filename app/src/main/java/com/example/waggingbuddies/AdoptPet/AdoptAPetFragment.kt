package com.example.waggingbuddies.AdoptPet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.waggingbuddies.AdoptPet.retrofit.RetrofitInstancePet
import com.example.waggingbuddies.DonateToShelter.ShelterDonationAdapter
import com.example.waggingbuddies.DonateToShelter.ShelterViewModel
import com.example.waggingbuddies.DonateToShelter.retrofit.RetrofitInstanceShelter
import com.example.waggingbuddies.R
import com.example.waggingbuddies.ShelterRegistration.ShelterRegistrationFragment
import com.example.waggingbuddies.databinding.FragmentAdoptAPetBinding
import com.example.waggingbuddies.databinding.FragmentDonateToSheltersBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AdoptAPetFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val itemAdapter = AdoptAPetAdapter()
    private lateinit var viewModel: PetViewModel
    lateinit var retrofitInstance : RetrofitInstancePet
    private lateinit var binding : FragmentAdoptAPetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentAdoptAPetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retrofitInstance = RetrofitInstancePet()
        viewModel = ViewModelProvider(this)[PetViewModel::class.java]


        binding.rvPets.layoutManager = LinearLayoutManager(requireContext())

        // itemAdapter = AllEventsAdapter()

        binding.rvPets.adapter = itemAdapter
        binding.rvPets.setHasFixedSize(true)
        // itemdapter.notifyDataSetChanged()
        //     binding.loadingCardAllevents.visibility = View.VISIBLE

        getPets()



    }

    private fun getPets() {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.getPet()
            delay(2000)
            this.launch(Dispatchers.Main) {
                //  binding.loadingCardAllevents.visibility = View.GONE
                itemAdapter.setPetList(viewModel.PetList)
                Log.i("Data",viewModel.PetList.toString())
            }
        }
    }



}