package com.example.silentzone.location_database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.VersionedParcelize
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class LocationData(
    @PrimaryKey
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double
): Parcelable
