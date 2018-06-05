package com.example.mygallery

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private val REQUEST_READ_EXTERNAL_STORAGE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // 권한이 허용되지 않음
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //
            } else {
                // 권한 요청
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_READ_EXTERNAL_STORAGE)
            }
        } else {
            // 권한이 이미 허용됨
            getAllPhotos()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_READ_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // 권한 허용됨
                    getAllPhotos()
                } else {
                    // 권한 거부
                    Toast.makeText(this, "권한 거부 됨", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    private fun getAllPhotos() {
        // 모든 사진 정보 가져오기
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,    // ①
                null,       // ②
                null,       // ③
                null,   // ④
                MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC")    // ⑤

        // 사진 경로를 프래그먼트로 전달하기
        val fragments = ArrayList<Fragment>()
        while (cursor.moveToNext()) {
            // 사진 경로 Uri 가지고 오기
            val uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
            fragments.add(PhotoFragment.newInstance(uri))
        }
        cursor?.close()

        // 어댑터
        val adapter = MyPagerAdapter(supportFragmentManager)
        adapter.updateFragments(fragments)
        viewPager.adapter = adapter

        // 3초마다 자동으로 슬라이드
        timer(period = 3000) {
            runOnUiThread {
                if (viewPager.currentItem < adapter.count - 1) {
                    viewPager.currentItem = viewPager.currentItem + 1
                } else {
                    viewPager.currentItem = 0
                }
            }
        }
    }
}
