package com.example.silentzone

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.silentzone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var checkLocationServiceIntent: Intent

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

        checkLocationServiceIntent=Intent(this,CheckLocationService::class.java)
        startService(checkLocationServiceIntent)

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