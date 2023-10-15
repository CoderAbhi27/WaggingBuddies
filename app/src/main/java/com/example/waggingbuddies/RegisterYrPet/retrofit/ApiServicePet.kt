package com.example.waggingbuddies.RegisterYrPet.retrofit

import com.example.waggingbuddies.DataClass.PetsDataClass
import com.example.waggingbuddies.DataClass.ShelterDataClass
import com.example.waggingbuddies.ShelterRegistration.retrofit.ApiResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServicePet {

        @POST("/pets")
        fun uplaodData(@Body RegData: PetsDataClass) : Call<ResponseBody>

}