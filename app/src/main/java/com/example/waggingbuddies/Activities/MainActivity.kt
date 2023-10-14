package com.example.waggingbuddies.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.waggingbuddies.AdoptPet.AdoptAPetFragment
import com.example.waggingbuddies.AdoptPet.retrofit.PetApiService
import com.example.waggingbuddies.ContactVet.ContactVeterinaryFragment
import com.example.waggingbuddies.DonateToShelter.DonateToSheltersFragment
import com.example.waggingbuddies.RegisterYrPet.RegisterYourPetFragment
import com.example.waggingbuddies.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        replaceFragment(AdoptAPetFragment())

        val bottomNavigationView : BottomNavigationView = findViewById(R.id.BottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener {

            if(it.itemId == R.id.adoptAPet){
                replaceFragment(AdoptAPetFragment())
            }
            if (it.itemId == R.id.contactVeterinary){
                replaceFragment(ContactVeterinaryFragment())
            }
            if (it.itemId == R.id.donateToShelters){
                replaceFragment(DonateToSheltersFragment())
            }
            if (it.itemId == R.id.registerYourPet){
                replaceFragment(RegisterYourPetFragment())
            }
            return@setOnItemSelectedListener true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, fragment)
        fragmentTransaction.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val status = data?.getStringExtra("Status")?.toLowerCase()
        if ((AppCompatActivity.RESULT_OK == resultCode) && status == "success") {
            Toast.makeText(this@MainActivity, "Transaction Successful", Toast.LENGTH_SHORT).show()
            onPaymentSuccessful(data.getStringExtra("shelterID"), data.getStringExtra("amount"))
        } else {
            Toast.makeText(this@MainActivity, "Transaction Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onPaymentSuccessful(shelterID: String?, amount: String?){
        Toast.makeText(this,"Thank you for donating rs$amount to $shelterID", Toast.LENGTH_SHORT).show()

        updateDatabase(shelterID, amount)
    }

    private val petApiService: PetApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://masterstack-o2dh.onrender.com/") // Replace with your API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        petApiService = retrofit.create(PetApiService::class.java)
    }


    private fun updateDatabase(shelterID: String?, donationAmount: String?) {
        shelterID?.let {
            // Create a RequestBody for the donation amount

            val amountRequestBody =
                donationAmount?.let { it1 -> RequestBody.create(MultipartBody.FORM, it1) }

            // Launch a coroutine on the IO dispatcher to make the network request
            MainScope().launch(Dispatchers.IO) {
                try {
                    // Call your API service to donate to a specific pet using a Multipart request
                    val response =
                        amountRequestBody?.let { it1 -> petApiService.donateToPet(shelterID, it1) }

                    if (response != null) {
                        if (response.isSuccessful) {
                            runOnUiThread {
                                Toast.makeText(this@MainActivity, "Donation Updated", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            val errorBody = response.errorBody()?.string()
                            runOnUiThread {
                                Toast.makeText(this@MainActivity, "Failed to update donation", Toast.LENGTH_SHORT).show()
                                // Handle the error further as needed
                                Log.e("UpdateDonation", "Error: $errorBody")
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Network error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}