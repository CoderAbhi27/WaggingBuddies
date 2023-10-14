package com.example.waggingbuddies.DonateToShelter.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstanceShelter {
    val client = OkHttpClient.Builder()
        .callTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS) // Increase the connect timeout
        .readTimeout(30, TimeUnit.SECONDS)    // Increase the read timeout
        .writeTimeout(30, TimeUnit.SECONDS)   // Increase the write timeout
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://masterstack-o2dh.onrender.com")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val shelterApiService : ShelterApiService = retrofit.create(ShelterApiService::class.java)
}