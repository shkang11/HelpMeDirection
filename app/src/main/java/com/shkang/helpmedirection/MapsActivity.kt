package com.shkang.helpmedirection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.PopupMenu

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.shkang.helpmedirection.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val mapOptionButton: ImageButton = binding.mapOptionsMenu
        val popupMenu = PopupMenu(this, mapOptionButton)
        popupMenu.menuInflater.inflate(R.menu.map_options, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            changeMap(menuItem.itemId)
            true
        }

        mapOptionButton.setOnClickListener {
            popupMenu.show()
        }

    }

    private fun changeMap(itemId: Int) {
        when(itemId)
        {
            R.id.normal_map -> mMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
            R.id.hybrid_map -> mMap?.mapType = GoogleMap.MAP_TYPE_HYBRID
            R.id.satellite_map -> mMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
            R.id.terrain_map -> mMap?.mapType = GoogleMap.MAP_TYPE_TERRAIN
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }
}