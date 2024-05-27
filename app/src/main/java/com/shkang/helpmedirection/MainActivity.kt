package com.shkang.helpmedirection

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.shkang.helpmedirection.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val etFromLocation: EditText = binding.etFromLocation
        val etToLocation: EditText = binding.etToLocation
        val btnGetDirection: Button = binding.btnGetDirection
        val btnViewMap: Button = binding.btnViewMap

        btnGetDirection.setOnClickListener {
            val userLocation: String = etFromLocation.getText().toString()
            val userDestination: String = etToLocation.getText().toString()
            if(userLocation == ""|| userDestination == "") {
                Toast.makeText(this@MainActivity, "위치 입력을 해주셔야해요", Toast.LENGTH_SHORT).show()
            } else {
                getDirections(userLocation, userDestination)
            }
        }

        btnViewMap.setOnClickListener {
            val intent = Intent(this@MainActivity, MapsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getDirections(from: String, to: String) {
        try {
            val uri = Uri.parse("https://www.google.com/maps/dir/$from/$to")
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                setPackage("com.google.android.apps.maps")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        } catch(exception: ActivityNotFoundException) {
            val uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps")
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        }
    }

}