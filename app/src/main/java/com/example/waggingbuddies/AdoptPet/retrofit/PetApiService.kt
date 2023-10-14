package com.example.waggingbuddies.AdoptPet.retrofit

import com.example.waggingbuddies.DataClass.PetsDataClass
import com.example.waggingbuddies.DataClass.ShelterDataClass
import com.example.waggingbuddies.ShelterRegistration.retrofit.ApiResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface PetApiService {

        @GET("/pets")
        suspend fun getPets(): Response<List<PetsDataClass>>

        @DELETE("pets/delete/{petId}")
        suspend fun deletePet(@Path("petId") petId: String): Response<ApiResponse>

        @Multipart
        @POST("pets/donate/{petId}")
        suspend fun donateToPet(
                @Path("petId") petId: String,
                @Part donationAmount: RequestBody
        ): Response<ApiResponse>

}