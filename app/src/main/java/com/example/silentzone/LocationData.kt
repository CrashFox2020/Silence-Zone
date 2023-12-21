package com.example.silentzone

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationData(
    @PrimaryKey
    val id: String,
    val longitude: Float,
    val latitude: Float
)
