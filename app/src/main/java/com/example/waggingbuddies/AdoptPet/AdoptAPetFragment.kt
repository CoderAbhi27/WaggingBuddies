package com.example.waggingbuddies.AdoptPet

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.waggingbuddies.AdoptPet.retrofit.RetrofitInstancePet
import com.example.waggingbuddies.R
import com.example.waggingbuddies.databinding.FragmentAdoptAPetBinding
import kotlinx.coroutines.CoroutineScope
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

        val list= resources.getStringArray(R.array.List)
        val root: View = binding.root
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.item_list,list)
        binding.petType.setAdapter(arrayAdapter)


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


        GlobalScope.launch(Dispatchers.IO) {
            viewModel.getPet()
            getPets()
//            binding.loadingCardAllevents.visibility = View.GONE
        }


        binding.petType.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                when (binding.petType.text.toString()) {
                    "All" -> getPets()
                    "Dogs" -> getDogs()
                    "Cats" -> getCat()
                    "Cows" -> getCow()
                    "Birds" -> getBird()
                }

            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {}
        })


    }

    private fun getPets() {
        CoroutineScope(Dispatchers.Main).launch {
            itemAdapter.setPetList(viewModel.PetList)
            Log.i("Dataa","all - ${viewModel.PetList.value}")
        }
    }

    private fun getDogs() {
        CoroutineScope(Dispatchers.Main).launch {
            itemAdapter.setPetList(viewModel.DogList)
            Log.i("Dataa","dog - ${viewModel.DogList.value}")
        }
    }

    private fun getCat() {
        CoroutineScope(Dispatchers.Main).launch {
            itemAdapter.setPetList(viewModel.CatList)
            Log.i("Dataa","cat - ${viewModel.CatList.value}")
        }
    }

    private fun getCow() {
        CoroutineScope(Dispatchers.Main).launch {
            itemAdapter.setPetList(viewModel.CowList)
            Log.i("Dataa","cow - ${viewModel.CowList.value}")
        }
    }

    private fun getBird() {
        CoroutineScope(Dispatchers.Main).launch {
            itemAdapter.setPetList(viewModel.BirdList)
            Log.i("Dataa","bird - ${viewModel.BirdList.value}")
        }
    }


}