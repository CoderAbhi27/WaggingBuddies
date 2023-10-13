package com.example.waggingbuddies.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
}