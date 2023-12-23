package com.example.silentzone

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationData(
    @PrimaryKey
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double
)
