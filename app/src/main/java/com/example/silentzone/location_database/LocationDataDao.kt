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


    @Query("SELECT * FROM locationdata WHERE latitude BETWEEN :leastlatitude AND :mostlatitude AND longitude BETWEEN :leastlongitude AND :mostlongitude")
    fun isCorrectCorrect(leastlatitude: Double, mostlatitude: Double,leastlongitude: Double, mostlongitude: Double): Boolean

    @Query("SELECT * FROM locationdata WHERE latitude>= :leastlatitude OR latitude<= :mostlatitude AND longitude BETWEEN :leastlongitude AND :mostlongitude")
    fun isWrongCorrect(leastlatitude: Double, mostlatitude: Double,leastlongitude: Double, mostlongitude: Double): Boolean

   @Query("SELECT * FROM locationdata WHERE latitude BETWEEN :leastlatitude AND :mostlatitude AND longitude>=:leastlongitude OR longitude<=:mostlongitude")
   fun isCorrectWrong(leastlatitude: Double, mostlatitude: Double,leastlongitude: Double, mostlongitude: Double): Boolean

   @Query("SELECT * FROM locationdata WHERE latitude>= :leastlatitude OR latitude<= :mostlatitude AND longitude>=:leastlongitude OR longitude<=:mostlongitude")
   fun isWrongWrong(leastlatitude: Double, mostlatitude: Double,leastlongitude: Double, mostlongitude: Double): Boolean
}