package com.example.silentzone.location_database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.silentzone.location_database.LocationData

@Dao
interface LocationDataDao {
    @Upsert
    suspend fun upsertLocationData(locationData: LocationData)

    @Delete
    suspend fun deleteLocationData(locationData: LocationData)

    @Query("SELECT * FROM locationdata")
    fun getLocationData(): LiveData<List<LocationData>>

    @Query("SELECT * from locationdata WHERE latitude= :latitude AND longitude= :longitude")
    fun isLocationExists(latitude: Double,longitude: Double): Boolean

}