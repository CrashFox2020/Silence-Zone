package com.example.silentzone

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationDataViewModel(application: Application): AndroidViewModel(application) {
    private val getLocationData: LiveData<List<LocationData>>
    private val repository: LocationDataRepository

    init {
        val locationDataDao=LocationDataDatabase.getDatabase(application).locationDataDao()
        repository= LocationDataRepository(locationDataDao)
        getLocationData=repository.getLocationData
    }

    fun upsertLocationData(locationData: LocationData){
        viewModelScope.launch (Dispatchers.IO){
            repository.upsertLocationData(locationData)
        }
    }
}