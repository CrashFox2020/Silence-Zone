package com.example.silentzone

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
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


        binding.playFAB.setOnClickListener(){
            Intent(applicationContext,LocationService::class.java).apply {
                action=LocationService.ACTION_START
                startService(this)
            }
        }

        binding.stopFAB.setOnClickListener(){
            Intent(applicationContext,LocationService::class.java).apply {
                action=LocationService.ACTION_STOP
                startService(this)
            }
        }
    }
}