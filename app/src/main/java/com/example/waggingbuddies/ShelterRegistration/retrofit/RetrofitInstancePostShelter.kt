package com.example.waggingbuddies.ShelterRegistration.retrofit

import com.example.waggingbuddies.DonateToShelter.retrofit.ShelterApiService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstancePostShelter {
    val gson = GsonBuilder()
        .setLenient()
        .create()

    val client = OkHttpClient.Builder()
        .callTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS) // Increase the connect timeout
        .readTimeout(30, TimeUnit.SECONDS)    // Increase the read timeout
        .writeTimeout(30, TimeUnit.SECONDS)   // Increase the write timeout
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://masterstack-sk23.onrender.com")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val shelterPostApiService : ApiService = retrofit.create(ApiService::class.java)
}