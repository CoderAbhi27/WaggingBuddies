package com.example.waggingbuddies.AdoptPet

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.waggingbuddies.AdoptPet.retrofit.RetrofitInstancePet
import com.example.waggingbuddies.DataClass.PetsDataClass
import retrofit2.Response

class PetViewModel(application: Application): AndroidViewModel(application) {
    var PetList = MutableLiveData<List<PetsDataClass>?>()
    private val retrofit: RetrofitInstancePet = RetrofitInstancePet() // Initialize Retrofit here
    private val context = getApplication<Application>().applicationContext

    suspend fun getPet()
    {
        Log.i("Data", "Function Called")
        try {
            val response: Response<List<PetsDataClass>> = retrofit.petApiService.getPets()
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    // Data fetched successfully
                    Log.d("Data", data.toString())
                    PetList.postValue(data)


                }
            } else {
                // Handle API error here
                // For example, you can throw a custom exception or return an empty list
                Log.e("API Error", "Response code: ${response.code()}")
            }
        }
        catch (e:Exception)
        {
            Log.e("Error", e.toString())
        }
    }
}