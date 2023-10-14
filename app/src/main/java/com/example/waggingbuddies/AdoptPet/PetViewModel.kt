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
    var DogList = MutableLiveData<List<PetsDataClass>?>()
    var CatList = MutableLiveData<List<PetsDataClass>?>()
    var CowList = MutableLiveData<List<PetsDataClass>?>()
    var BirdList = MutableLiveData<List<PetsDataClass>?>()

    suspend fun getPet()
    {
        Log.i("Data", "Function Called")
        try {
            val response: Response<List<PetsDataClass>> = retrofit.petApiService.getPets()
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    data.filter {
                        it.petAdoptionTime < System.currentTimeMillis()
                    }
                    // Data fetched successfully
                    Log.d("Data", data.toString())
                    PetList.postValue(data)

                    data.filter {
                        it.petAdoptionTime < System.currentTimeMillis()
                                && it.petType=="Dog"
                    }
                    // Data fetched successfully
                    Log.d("Data", data.toString())
                    DogList.postValue(data)

                    data.filter {
                        it.petAdoptionTime < System.currentTimeMillis()
                                && it.petType=="Cat"
                    }
                    // Data fetched successfully
                    Log.d("Data", data.toString())
                    CatList.postValue(data)

                    data.filter {
                        it.petAdoptionTime < System.currentTimeMillis()
                                && it.petType=="Cow"
                    }
                    // Data fetched successfully
                    Log.d("Data", data.toString())
                    CowList.postValue(data)

                    data.filter {
                        it.petAdoptionTime < System.currentTimeMillis()
                                && it.petType=="Bird"
                    }
                    // Data fetched successfully
                    Log.d("Data", data.toString())
                    BirdList.postValue(data)


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