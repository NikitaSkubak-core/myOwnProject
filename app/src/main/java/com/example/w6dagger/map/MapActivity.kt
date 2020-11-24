package com.example.w6dagger.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources.NotFoundException
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.w6dagger.R
import com.example.w6dagger.main.ViewModelProviderFactory
import com.example.w6dagger.request.RequestViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.robin.locationgetter.EasyLocation
import com.robin.locationgetter.EasyLocation.EasyLocationCallBack
import dagger.android.support.DaggerAppCompatActivity
import java.util.*
import javax.inject.Inject

class MapActivity : DaggerAppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var context: Context

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private lateinit var requestViewModel: RequestViewModel
    lateinit var locationManager: LocationManager
    lateinit var request: String
    lateinit var userName: String
    var right = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_maps)
        requestViewModel = injectViewModel(viewModelFactory)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = SupportMapFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, mapFragment).commit()
        mapFragment.getMapAsync(this)
        userName = intent.getStringExtra(EXTRA_REPLY)!!
        Log.d("username", userName)
        context = this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.map_options, menu)
        return true
    }

    private val permissionlistener = object : PermissionListener {
        override fun onPermissionGranted() {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {
            Toast.makeText(context, "Permission Denied\n$deniedPermissions", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        TedPermission.with(context)
            .setPermissionListener(permissionlistener)
            .setDeniedMessage(
                "If you reject permission,you can not use this service\n" +
                        "\nPlease turn on permissions at [Setting] > [Permission]"
            )
            .setPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .check()
        mMap = googleMap
        locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        getCurrentLocation()
        setMapLongClick(mMap)
        setPoiClick(mMap)
        try {
            // Customize the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this, R.raw.map_style
                )
            )
            if (!success) {
                Log.e("ColorStyle", "Style parsing failed.")
            }
        } catch (e: NotFoundException) {
            Log.e("ColorStyle", "Can't find style. Error: ", e)
        }
        enableMyLocation()
        setInfoImageFromWindowClick(mMap)
    }

    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                latLng.latitude,
                latLng.longitude
            )
            map.clear()
            map.addMarker(
                MarkerOptions().position(latLng).title(getString(R.string.dropped_pin))
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )
        }
    }

    private fun setPoiClick(map: GoogleMap?) {
        map!!.setOnPoiClickListener { poi ->
            val poiMarker = mMap.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
            )
            poiMarker.showInfoWindow()
            poiMarker.tag = "poi"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Change the map type based on the user's selection.
        return when (item.itemId) {
            R.id.normal_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                true
            }
            R.id.hybrid_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                true
            }
            R.id.satellite_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                true
            }
            R.id.terrain_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                locationListener
            )
            EasyLocation(this, callBack)
            current_location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            centreMapOnLocation(current_location, "Your Location")
        }
    }

    private val callBack: EasyLocationCallBack = object : EasyLocationCallBack {
        override fun permissionDenied() {}
        override fun locationSettingFailed() {}
        override fun getLocation(location: Location) {
            current_location = location
        }
    }

    private fun centreMapOnLocation(location: Location?, title: String) {
        val userLocation = LatLng(location!!.latitude, location.longitude)
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(userLocation).title(title))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 12f))
    }

    private val locationListener = LocationListener { location ->
        if (right) {
            centreMapOnLocation(location, "Your Location")
            right = false
        }
    }

    private fun setInfoImageFromWindowClick(map: GoogleMap) {
        map.setOnInfoWindowClickListener { marker ->
            if (marker.tag === "poi") {
                request = marker.title!!
                requestViewModel.insertRequest(userName, request)
            }
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.finalwee4project.REPLY"
        var current_location: Location? = null
    }

    private inline fun <reified T : ViewModel> injectViewModel(factory: ViewModelProvider.Factory): T {
        return ViewModelProviders.of(this, factory)[T::class.java]
    }
}