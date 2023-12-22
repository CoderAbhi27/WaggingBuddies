package com.example.waggingbuddies.DonateToShelter

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.waggingbuddies.DataClass.ShelterDataClass
import com.example.waggingbuddies.DonateToShelter.retrofit.RetrofitInstanceShelter
import retrofit2.Response

class ShelterViewModel(application: Application): AndroidViewModel(application) {
    var ShelterList = MutableLiveData<List<ShelterDataClass>?>()
    private val retrofit: RetrofitInstanceShelter = RetrofitInstanceShelter() // Initialize Retrofit here
    private val context = getApplication<Application>().applicationContext

    suspend fun getShelter()
    {
        Log.i("Data", "Function Called")
        try {
            val response: Response<List<ShelterDataClass>> = retrofit.shelterApiService.getShelter()
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    // Data fetched successfully
                    Log.d("Data", data.toString())
                    ShelterList.postValue(data)


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
            throw e
        }
    }
}