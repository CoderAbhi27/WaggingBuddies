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
import com.example.waggingbuddies.databinding.FragmentAdoptAPetBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AdoptAPetFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var itemAdapter : AdoptAPetAdapter
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
        itemAdapter = AdoptAPetAdapter(requireContext())
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

    private fun getDogs() {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.getPet()
            delay(2000)
            this.launch(Dispatchers.Main) {
                //  binding.loadingCardAllevents.visibility = View.GONE
                itemAdapter.setPetList(viewModel.DogList)
                Log.i("Data",viewModel.DogList.toString())
            }
        }
    }

    private fun getCat() {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.getPet()
            delay(2000)
            this.launch(Dispatchers.Main) {
                //  binding.loadingCardAllevents.visibility = View.GONE
                itemAdapter.setPetList(viewModel.CatList)
                Log.i("Data",viewModel.CatList.toString())
            }
        }
    }
    private fun getCow() {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.getPet()
            delay(2000)
            this.launch(Dispatchers.Main) {
                //  binding.loadingCardAllevents.visibility = View.GONE
                itemAdapter.setPetList(viewModel.CowList)
                Log.i("Data",viewModel.CowList.toString())
            }
        }
    }

    private fun getBird() {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.getPet()
            delay(2000)
            this.launch(Dispatchers.Main) {
                //  binding.loadingCardAllevents.visibility = View.GONE
                itemAdapter.setPetList(viewModel.BirdList)
                Log.i("Data",viewModel.BirdList.toString())
            }
        }
    }


}