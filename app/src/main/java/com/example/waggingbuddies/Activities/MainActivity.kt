package com.example.waggingbuddies.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.waggingbuddies.AdoptPet.AdoptAPetFragment
import com.example.waggingbuddies.ContactVet.ContactVeterinaryFragment
import com.example.waggingbuddies.DonateToShelter.DonateToSheltersFragment
import com.example.waggingbuddies.RegisterYrPet.RegisterYourPetFragment
import com.example.waggingbuddies.R
import com.google.android.material.bottomnavigation.BottomNavigationView



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



    private fun updateDatabase(shelterID: String?, amount: String?) {
        //Update database
    }
}