package com.example.silentzone

import androidx.lifecycle.LiveData

class LocationDataRepository(private val locationDataDao: LocationDataDao) {
    val getLocationData: LiveData<List<LocationData>> = locationDataDao.getLocationData()

    suspend fun upsertLocationData(locationData: LocationData){
        locationDataDao.upsertLocationData(locationData)
    }
}