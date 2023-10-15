package com.example.waggingbuddies.DataClass

data class ShelterDataClass(
    val name: String,
    val address: String,
    val totalCapacity: List<Int>,
    val currentStrength: List<Int>,
    val donationsRecieved: Int,
    val shelterID: String?= null
)
