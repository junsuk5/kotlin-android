package com.example.gpsmap

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private val REQUEST_ACCESS_FINE_LOCATION = 1000

    private lateinit var mMap: GoogleMap    // 1
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: MyLocationCallBack

    private val polylineOptions = PolylineOptions().width(5f).color(Color.RED)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 화면이 꺼지지 않게 하기
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        // 세모 모드로 화면 고정
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_maps)

        // SupportMapFragment를 가져와서 지도가 준비되면 알림을 받습니다 2
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationInit()
    }

    // 위치 정보를 얻기 위한 각종 초기화
    private fun locationInit() {
        fusedLocationProviderClient = FusedLocationProviderClient(this)

        locationCallback = MyLocationCallBack()

        locationRequest = LocationRequest()
        // GPS 우선
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        // 업데이트 인터벌
        // 위치 정보가 없을 때는 업데이트 안 함
        // 상황에 따라 짧아질 수 있음, 정확하지 않음
        // 다른 앱에서 짧은 인터벌로 위치 정보를 요청하면 짧아질 수 있음
        locationRequest.interval = 10000
        // 정확함. 이것보다 짧은 업데이트는 하지 않음
        locationRequest.fastestInterval = 5000
    }

    /**
     * 사용 가능한 맵을 조작합니다.
     * 지도가 사용될 준비가 되면 이 콜백이 호출됩니다.
     * 여기서 마커나 선을 추가하거나 청취자를 추가하거나 카메라를 이동할 수 있습니다.
     * 호주 시드니 근처에 표식을 추가하고 있습니다.
     * Google Play 서비스가 기기에 설치되어 있지 않으면 사용자에게 설치하라는 메시지가 표시됩니다.
     * SupportMapFragment 안에 있습니다. 이 메소드는 한 번만 호출됩니다.
     * Google Play 서비스가 설치되고 앱으로 돌아 옵니.
     */
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap    // 3

        // 시드니에 마커를 추가하고 카메라를 이동합니다     4
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        // 권한 요청
        permissionCheck(cancel = {
            showLocationInfoDialog()
        }, ok = {
            mMap.isMyLocationEnabled = true
        })
    }

    override fun onResume() {
        super.onResume()

        permissionCheck(cancel = {
            // 위치 정보가 필요한 이유 다이얼로그 표시
            showLocationInfoDialog()
        }, ok = {
            // 현재 위치를 주기적으로 요청
            addLocationListener()
        })
    }

    override fun onPause() {
        super.onPause()
        // 현재 위치 요청을 취소
        removeLocationListener()
    }

    private fun showLocationInfoDialog() {
        alert("현재 위치 정보를 얻기 위해서는 위치 권한이 필요합니다", "권한이 필요한 이유") {
            yesButton {
                // 권한 요청
                ActivityCompat.requestPermissions(this@MapsActivity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_ACCESS_FINE_LOCATION)
            }
            noButton { }
        }.show()
    }

    @SuppressLint("MissingPermission")
    private fun addLocationListener() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.myLooper());
    }

    private fun removeLocationListener() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_ACCESS_FINE_LOCATION -> {
                if ((grantResults.isNotEmpty()
                                && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // 권한 허용됨
                    addLocationListener()
                } else {
                    // 권한 거부
                    toast("권한 거부 됨")
                }
                return
            }
        }
    }

    private fun permissionCheck(cancel: () -> Unit, ok: () -> Unit) {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 권한이 허용되지 않음 ②
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                cancel()
            } else {
                // 권한 요청 ④
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_ACCESS_FINE_LOCATION)
            }
        } else {
            ok()
        }
    }

    inner class MyLocationCallBack : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation

            location?.run {
                // 14 level로 확대하며 현재 위치로 애니메이션 이동
                val latLng = LatLng(latitude, longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

//                latLngs.add(latLng)
                polylineOptions.add(latLng)

                // 선 그리기
                mMap.addPolyline(polylineOptions)
            }

        }
    }

}
