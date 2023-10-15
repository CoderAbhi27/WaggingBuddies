package com.example.waggingbuddies.Activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.waggingbuddies.AdoptPet.retrofit.PetApiService
import com.example.waggingbuddies.DataClass.PetsDataClass
import com.example.waggingbuddies.R
import com.example.waggingbuddies.ShelterRegistration.retrofit.ApiResponse
import com.example.waggingbuddies.databinding.ActivityPetDescriptionBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.Path

class petDescription : AppCompatActivity() {

    private lateinit var data : PetsDataClass
    private lateinit var binding: ActivityPetDescriptionBinding
  //  data = intent.getSerializableExtra("petData") as PetsDataClass
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPetDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

      val petName = intent.getStringExtra("petName") ?: ""
      val petType = intent.getStringExtra("petType") ?: ""
      val petAge = intent.getIntExtra("petAge", 0) ?: 0 // Assuming petAge is an int, provide a default value if needed
      val petImageUrl = intent.getStringExtra("petImageUrl") ?: ""
      val petBreed = intent.getStringExtra("petBreed") ?: ""
      val petHealth = intent.getIntExtra("petHealth",0) ?: 0
      val petOwner = intent.getStringExtra("petOwner") ?: ""
      val petAddress = intent.getStringExtra("petAddress") ?: ""
      val adoptionMsg = intent.getStringExtra("adoptionMsg") ?: ""
      val gender = intent.getBooleanExtra("gender",false) ?: false
      val petOwnerEmail= intent.getStringExtra("email") ?: ""
      val adoptionTime=intent.getLongExtra("time",10000L) ?: 10000L
      val petId= intent.getStringExtra("id") ?: ""
      data= PetsDataClass(petType,petName,petAge,petBreed,petOwner,petOwnerEmail,petHealth,adoptionTime,petAddress,petImageUrl,adoptionMsg,gender,petId)
        //get data in data
if (data != null)
{
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
            data.petID?.let { it1 -> deletePet(it1) }
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener{ dialog, which-> })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

}
else
    Toast.makeText(this,"Null Pointer",Toast.LENGTH_SHORT).show()



    }


    private val petApiService: PetApiService
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://masterstack-o2dh.onrender.com") // Replace with your API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        petApiService = retrofit.create(PetApiService::class.java)
    }
    private fun deletePet(petId: String) {
        MainScope().launch(Dispatchers.IO) {
            try {
                val response = petApiService.deletePet(petId)

                if (response.isSuccessful) {
                    runOnUiThread {
                        Toast.makeText(this@petDescription, "Pet Adopted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    runOnUiThread {
                        Toast.makeText(this@petDescription, "Failed to delete pet", Toast.LENGTH_SHORT).show()
                        // Handle the error further as needed
                        Log.e("DeletePet", "Error: $errorBody")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@petDescription, "Network error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}