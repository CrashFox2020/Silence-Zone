package com.example.silentzone.location_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [LocationData::class],
    version = 1
)
abstract class LocationDataDatabase: RoomDatabase() {
    abstract fun locationDataDao(): LocationDataDao

    companion object {
        @Volatile
        private var INSTANCE: LocationDataDatabase?=null

        fun getDatabase(context: Context): LocationDataDatabase {
            val tempInstance= INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    LocationDataDatabase::class.java,
                    "locationdata"
                ).build()
                INSTANCE =instance
                return instance
            }
        }
    }
}