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
import java.net.URLEncoder

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
        // 예제 좌표와 이름 사용 (실제 좌표와 이름으로 교체해야 함)
        val destinationLatitude = "37.5665"  // 도착지 위도
        val destinationLongitude = "126.9780"  // 도착지 경도
        val destinationName = Uri.encode(to)  // 도착지 이름

        val url = "nmap://navigation?dlat=$destinationLatitude&dlng=$destinationLongitude&dname=$destinationName&appname=com.example.myapp"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            setPackage("com.nhn.android.nmap")
        }

        try {
            startActivity(intent)
        } catch (e: Exception) {
            val marketIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("market://details?id=com.nhn.android.nmap")
            }
            startActivity(marketIntent)
        }
    }

}