package com.example.waggingbuddies.AdoptPet

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.waggingbuddies.AdoptPet.retrofit.RetrofitInstancePet
import com.example.waggingbuddies.DataClass.PetsDataClass
import kotlinx.coroutines.delay
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
//        delay(5000)
        try {
            val response: Response<List<PetsDataClass>> = retrofit.petApiService.getPets()
            if (response.isSuccessful) {
                var data = response.body()
                if (data != null) {
                    data = data.filter {
                        it.petAdoptionTime < System.currentTimeMillis()
                    }
                    // Data fetched successfully
                    Log.d("Data", data.toString())
                    PetList.postValue(data)

                    val dogs = data.filter {
                        it.petType=="Dog"
                    }
                    // Data fetched successfully
                    Log.d("Data", dogs.toString())
                    DogList.postValue(dogs)

                    val cats = data.filter {
                        it.petType=="Cat"
                    }
                    // Data fetched successfully
                    Log.d("Data", cats.toString())
                    CatList.postValue(cats)

                    val cows = data.filter {
                        it.petType=="Cow"
                    }
                    // Data fetched successfully
                    Log.d("Data", cows.toString())
                    CowList.postValue(cows)

                    val birds = data.filter {
                        it.petType=="Bird"
                    }
                    // Data fetched successfully
                    Log.d("Data", birds.toString())
                    BirdList.postValue(birds)


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