package com.example.waggingbuddies.ShelterRegistration.retrofit

import com.example.waggingbuddies.DataClass.ShelterDataClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/shelter")
    fun uplaodData(@Body RegData: ShelterDataClass) : Call<ResponseBody>
}