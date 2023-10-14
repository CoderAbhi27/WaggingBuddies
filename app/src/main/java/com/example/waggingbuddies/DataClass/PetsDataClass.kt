package com.example.waggingbuddies.DataClass

import android.media.Image
import java.io.Serializable

data class PetsDataClass (
    val petType: String,
    val petName: String,
    val petAge: Int,
    val petBreed: String,
    val petOwnersName: String,
    val petOwnerEmail: String,
    val petHealth: Int,
    val petAdoptionDays: Int,
    val petAddress: String,
    val petImageURL: String,
    val adoptionMsg: String,
    val gender: Boolean //true-> female, false-> male
): Serializable