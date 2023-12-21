package com.example.silentzone

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Upsert
    suspend fun upsertLocation(locationData: LocationData)

    @Delete
    suspend fun deleteLocation(locationData: LocationData)

    @Query("SELECT * FROM locationdata")
    fun getLocations(): Flow<List<LocationData>>
}