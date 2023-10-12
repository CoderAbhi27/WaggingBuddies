package com.example.waggingbuddies.ViewModels.RetrofitShelter

import com.example.waggingbuddies.DataClass.PetsDataClass
import com.example.waggingbuddies.DataClass.ShelterDataClass
import retrofit2.Response
import retrofit2.http.GET

interface ShelterApiService {
    @GET("")
    suspend fun getShelter(): Response<List<ShelterDataClass>>
}