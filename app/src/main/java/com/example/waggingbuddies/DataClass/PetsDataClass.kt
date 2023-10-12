package com.example.waggingbuddies.DataClass

import android.media.Image

data class PetsDataClass (
    val petType: String,
    val petName: String,
    val petAge: Int,
    val petBreed: String,
    val petOwnersName: String,
    val petHealth: Int,
    val petAdoptionDays: Int,
    val petAddress: Int,
    val petImageURL: String,
    val adoptionTime: Long,
    val adoptionMsg: String
)