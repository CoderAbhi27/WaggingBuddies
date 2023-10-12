package com.example.waggingbuddies.ShelterRegistration.retrofit

import com.example.waggingbuddies.DataClass.ShelterDataClass
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/postRequest")
    fun uplaodData(@Body RegData: ShelterDataClass) : Call<ApiResponse>
}