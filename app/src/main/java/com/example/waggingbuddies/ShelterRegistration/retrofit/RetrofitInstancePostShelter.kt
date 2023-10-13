package com.example.waggingbuddies.ShelterRegistration.retrofit

import com.example.waggingbuddies.DonateToShelter.retrofit.ShelterApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstancePostShelter {
    val client = OkHttpClient.Builder()
        .callTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS) // Increase the connect timeout
        .readTimeout(30, TimeUnit.SECONDS)    // Increase the read timeout
        .writeTimeout(30, TimeUnit.SECONDS)   // Increase the write timeout
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.239.150:3000")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val shelterPostApiService : ApiService = retrofit.create(ApiService::class.java)
}