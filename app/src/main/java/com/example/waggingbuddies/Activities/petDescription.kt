package com.example.waggingbuddies.Activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.waggingbuddies.DataClass.PetsDataClass
import com.example.waggingbuddies.R
import com.example.waggingbuddies.databinding.ActivityPetDescriptionBinding
import com.example.waggingbuddies.databinding.ActivitySignUpBinding
import com.squareup.picasso.Picasso

class petDescription : AppCompatActivity() {

    private lateinit var data : PetsDataClass
    private lateinit var binding: ActivityPetDescriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPetDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get data in data
        data = intent.getSerializableExtra("petData") as PetsDataClass

        Picasso.get().load(data.petImageURL).into(binding.imageOfPet)
        binding.age.text = data.petAge.toString()
        binding.breed.text = data.petBreed
        binding.petName.text = data.petName
        binding.health.text = data.petHealth.toString()
        binding.type.text = data.petType
        binding.ownerName.text = data.petOwnersName
        binding.msg.text = data.adoptionMsg
        binding.adress.text = data.petAddress
        if(data.gender){
            binding.female.visibility = View.VISIBLE
            binding.male.visibility = View.GONE
        } else {
            binding.male.visibility = View.VISIBLE
            binding.female.visibility = View.GONE
        }

        binding.button.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("ADOPT THIS BUDDY?")
            builder.setPositiveButton("CONFIRM", DialogInterface.OnClickListener{ dialog, which->
                Toast.makeText(this, "Yayy!! You adopted a buddy!", Toast.LENGTH_LONG)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                deletePet(data.petID)
            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener{ dialog, which-> })
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }



    }



    private fun deletePet(petID: String?) {

    }


}