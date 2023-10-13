package com.example.waggingbuddies.DonateToShelter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.waggingbuddies.ShelterRegistration.ShelterRegistrationFragment
import com.example.waggingbuddies.R
import com.example.waggingbuddies.DonateToShelter.retrofit.RetrofitInstanceShelter
import com.example.waggingbuddies.databinding.FragmentDonateToSheltersBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DonateToSheltersFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var itemAdapter :  ShelterDonationAdapter
    private lateinit var viewModel: ShelterViewModel
    lateinit var retrofitInstance : RetrofitInstanceShelter
    private lateinit var binding : FragmentDonateToSheltersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentDonateToSheltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retrofitInstance = RetrofitInstanceShelter()
        viewModel = ViewModelProvider(this)[ShelterViewModel::class.java]


        binding.rvShelter.layoutManager = LinearLayoutManager(requireContext())

        // itemAdapter = AllEventsAdapter()

        itemAdapter = ShelterDonationAdapter(requireContext())
        binding.rvShelter.adapter = itemAdapter
        binding.rvShelter.setHasFixedSize(true)
        // itemdapter.notifyDataSetChanged()
   //     binding.loadingCardAllevents.visibility = View.VISIBLE


        getShelters()


        binding.fab.setOnClickListener {
            val fragmentTransaction : FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame, ShelterRegistrationFragment())
            fragmentTransaction.commit()
        }

    }

    private fun getShelters() {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.getShelter()
            delay(2000)
            this.launch(Dispatchers.Main) {
              //  binding.loadingCardAllevents.visibility = View.GONE
                itemAdapter.setShelterList(viewModel.ShelterList)
                Log.i("Data",viewModel.ShelterList.toString())
            }
        }
    }

}