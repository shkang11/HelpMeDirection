package com.shkang.helpmedirection

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
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
            val from: String = etFromLocation.getText().toString()
            val to: String = etToLocation.getText().toString()
            if(from.isBlank() || to.isBlank()) {
                Toast.makeText(this@MainActivity, "위치 입력을 해주셔야해요", Toast.LENGTH_SHORT).show()
            } else {
                getLatitudeLongitude(from, to)
            }
        }

        btnViewMap.setOnClickListener {
            val intent = Intent(this@MainActivity, MapsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getLatitudeLongitude(from: String, to: String) {
        val geocoder = Geocoder(this)
        var startPoint: LatLng? = null
        var endPoint: LatLng? = null

        // 출발지 주소를 위도와 경도로 변환
        val startAddresses: List<Address> = geocoder.getFromLocationName(from, 1)?.toList() ?: emptyList()
        if (startAddresses.isNotEmpty()) {
            startPoint = LatLng(startAddresses[0].latitude, startAddresses[0].longitude)
        }

        // 도착지 주소를 위도와 경도로 변환
        val destinationAddresses: List<Address> = geocoder.getFromLocationName(to, 1)?.toList() ?: emptyList()
        if (destinationAddresses.isNotEmpty()) {
            endPoint = LatLng(destinationAddresses[0].latitude, destinationAddresses[0].longitude)
        }

        if (startPoint != null && endPoint != null) {
            getDirections(startPoint, endPoint, from, to)
        } else {
            Toast.makeText(this@MainActivity, "주소를 변환할 수 없습니다", Toast.LENGTH_SHORT).show()
        }
    }
    private fun getDirections(startPoint: LatLng, endPoint: LatLng, from: String, to: String) {
        val url = "nmap://route/public?" +
                "slat=${startPoint.latitude}&slng=${startPoint.longitude}" +
                "&dlat=${endPoint.latitude}&dlng=${endPoint.longitude}" +
                "&sname=${Uri.encode(from)}&dname=${Uri.encode(to)}" +
                "&appname=com.shkang.helpmedirection"

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