package com.example.waggingbuddies.AdoptPet.retrofit

import com.example.waggingbuddies.DataClass.PetsDataClass
import com.example.waggingbuddies.DataClass.ShelterDataClass
import retrofit2.Response
import retrofit2.http.GET

interface PetApiService {

        @GET("/pets")
        suspend fun getPets(): Response<List<PetsDataClass>>

}