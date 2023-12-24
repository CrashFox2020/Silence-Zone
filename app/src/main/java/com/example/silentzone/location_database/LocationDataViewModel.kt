package com.example.silentzone.location_database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.silentzone.location_database.LocationData
import com.example.silentzone.location_database.LocationDataDatabase
import com.example.silentzone.location_database.LocationDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationDataViewModel(application: Application): AndroidViewModel(application) {
    val getLocationData: LiveData<List<LocationData>>
    private val repository: LocationDataRepository

    init {
        val locationDataDao= LocationDataDatabase.getDatabase(application).locationDataDao()
        repository= LocationDataRepository(locationDataDao)
        getLocationData=repository.getLocationData
    }

    fun upsertLocationData(locationData: LocationData){
        viewModelScope.launch (Dispatchers.IO){
            repository.upsertLocationData(locationData)
        }
    }

    fun deleteLocationData(locationData: LocationData){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteLocationData(locationData)
        }
    }
}