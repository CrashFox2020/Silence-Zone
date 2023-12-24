package com.example.silentzone.location_database

import androidx.lifecycle.LiveData
import com.example.silentzone.location_database.LocationData
import com.example.silentzone.location_database.LocationDataDao

class LocationDataRepository(private val locationDataDao: LocationDataDao) {
    val getLocationData: LiveData<List<LocationData>> = locationDataDao.getLocationData()

    suspend fun upsertLocationData(locationData: LocationData){
        locationDataDao.upsertLocationData(locationData)
    }

    suspend fun deleteLocationData(locationData: LocationData){
        locationDataDao.deleteLocationData(locationData)
    }
}