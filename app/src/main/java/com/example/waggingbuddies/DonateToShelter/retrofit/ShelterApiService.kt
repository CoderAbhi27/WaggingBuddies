package com.example.waggingbuddies.DonateToShelter.retrofit

import com.example.waggingbuddies.DataClass.ShelterDataClass
import retrofit2.Response
import retrofit2.http.GET

interface ShelterApiService {
    @GET("/shelter")
    suspend fun getShelter(): Response<List<ShelterDataClass>>
}