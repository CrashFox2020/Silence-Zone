package com.example.silentzone

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import com.example.silentzone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            0
        )


        setContentView(binding.root)
        var nm: NotificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M&&!nm.isNotificationPolicyAccessGranted){
            startActivity(Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS))
        }
        binding.startButton.setOnClickListener(){

            Intent(applicationContext,LocationService::class.java).apply {
                action=LocationService.ACTION_START
                startService(this)
            }

        }

        binding.stopButton.setOnClickListener(){

            Intent(applicationContext,LocationService::class.java).apply {
                action=LocationService.ACTION_STOP
                startService(this)
            }

        }



    }




}