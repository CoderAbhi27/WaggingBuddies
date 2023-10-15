package com.example.waggingbuddies.ContactVet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.waggingbuddies.DataClass.VetsDataCLass
import com.example.waggingbuddies.databinding.FragmentContactVeterinaryBinding
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.InputStream


class ContactVeterinaryFragment : Fragment() {

    private lateinit var binding : FragmentContactVeterinaryBinding

    private lateinit var vetsRecyclerView : RecyclerView
    private var dataList : ArrayList<VetsDataCLass> = ArrayList<VetsDataCLass>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentContactVeterinaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.rvVets.layoutManager = LinearLayoutManager(requireContext())

        getData()

        val itemAdapter = ContactVeterinaryAdapter(dataList)
        binding.rvVets.adapter = itemAdapter
        binding.rvVets.setHasFixedSize(true)


        itemAdapter.setOnItemClickListener(object: ContactVeterinaryAdapter.onItemClickListener{

            override fun onItemClick(contactNo: String) {
                val call: Uri = Uri.parse("tel:$contactNo")
                val intent = Intent(Intent.ACTION_DIAL, call)
                startActivity(intent)
            }

        })



    }

    private fun getData() {
        val inputStream : InputStream = requireContext().assets.open("vet.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val json = String(buffer, Charsets.UTF_8)

        val gson = Gson()
        val vets = gson.fromJson(json, Array<VetsDataCLass>::class.java)
        dataList.addAll(vets)
    }


}