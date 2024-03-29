package com.example.silentzone.location_tracking

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.silentzone.CommonVariables
import com.example.silentzone.location_database.LocationDataDao
import com.example.silentzone.location_database.LocationDataDatabase
import com.example.silentzone.R
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LocationService: Service() {
    private val serviceScope = CoroutineScope(SupervisorJob()+Dispatchers.IO)
    private lateinit var locationClient: LocationClient
    private lateinit var locationDataDao: LocationDataDao


    override fun onBind(p0: Intent?): IBinder? {
        return null


    }

    override fun onCreate() {
        super.onCreate()
        locationClient= DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
        locationDataDao= LocationDataDatabase.getDatabase(applicationContext).locationDataDao()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action){
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(){
        val notification=NotificationCompat.Builder(this,"location")
            .setContentTitle("Tracking Location")
            .setContentText("Location: null")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setOngoing(true)
        val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var audioManager=getSystemService(Context.AUDIO_SERVICE) as AudioManager

        locationClient.getLocationUpdate(10L)
            .catch { e->e.printStackTrace() }
            .onEach { location ->
                val lat=location.latitude.toString()
                val long=location.longitude.toString()
                val updateNotification=notification.setContentText(
                    "Location: ($lat,$long)"
                )
                CommonVariables.currentLatitude =lat.toDouble()
                CommonVariables.currentLongitude =long.toDouble()
                notificationManager.notify(1,updateNotification.build())
                val locationExists=isLocationExists()

                if(locationExists && audioManager.ringerMode==AudioManager.RINGER_MODE_NORMAL){
                    Log.i("Hello1","Yes")
                    audioManager.ringerMode=AudioManager.RINGER_MODE_VIBRATE
                }
                else if(!locationExists && audioManager.ringerMode==AudioManager.RINGER_MODE_VIBRATE){
                    Log.i("Hello","No")
                    audioManager.ringerMode=AudioManager.RINGER_MODE_NORMAL
                }

            }
            .launchIn(serviceScope)
        startForeground(1,notification.build())

    }

    private fun stop(){
        var audioManager=getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.ringerMode=AudioManager.RINGER_MODE_NORMAL
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START="ACTION_START"
        const val ACTION_STOP="ACTION_STOP"
    }

    private fun isLocationExists(): Boolean{

        val multiple=0.0145//1mile is about 0.0145 degrees
        var lowLatitude=CommonVariables.currentLatitude-CommonVariables.range*multiple
        var highLatitude=CommonVariables.currentLatitude+CommonVariables.range*multiple
        var lowLongitude=CommonVariables.currentLongitude-CommonVariables.range*multiple
        var highLongitude=CommonVariables.currentLongitude+CommonVariables.range*multiple
        var lat=0
        var long=0
        if(lowLatitude>=-90&&highLatitude<=90&&lowLongitude>=-180&&highLongitude<=180){
            Log.i("Hello","Yes")
            return locationDataDao.isCorrectCorrect(lowLatitude,highLatitude,lowLongitude,highLongitude)
        }
        if(lowLatitude<-90){
            lowLatitude=90-(-90-lowLatitude)
            lat=1
        }
        else if(highLatitude>90){
            highLatitude=-90+(highLatitude-90)
            lat=1
        }
        if(lowLongitude<-180){
            lowLongitude=180-(-180-lowLongitude)
            long=1
        }
        else if(highLongitude>180){
            highLongitude=180+(highLongitude-180)
            long=1
        }
        if(lat==0&&long==0){
            return locationDataDao.isCorrectCorrect(lowLatitude,highLatitude,lowLongitude,highLongitude)
        }
        else if(lat==0&&long==1){
            return locationDataDao.isCorrectWrong(lowLatitude,highLatitude,lowLongitude,highLongitude)
        }
        else if(lat==1&&long==0){
            return locationDataDao.isWrongCorrect(lowLatitude,highLatitude,lowLongitude,highLongitude)
        }

        return locationDataDao.isWrongWrong(lowLatitude,highLatitude,lowLongitude,highLongitude)


    }
}