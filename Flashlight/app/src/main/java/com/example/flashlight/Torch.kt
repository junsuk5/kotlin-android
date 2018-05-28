package com.example.flashlight

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager


class Torch(context: Context) {
    private val TAG = Torch::class.qualifiedName

    private var cameraId: String? = null
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    init {
        cameraId = getCameraId()
    }

    fun flashOn() {
//        try {
            cameraManager.setTorchMode(cameraId, true)
//        } catch (e: CameraAccessException) {
//            Log.d(TAG, "flash on fail : " + e.localizedMessage)
//        }

    }

    fun flashOff() {
//        try {
            cameraManager.setTorchMode(cameraId, false)
//        } catch (e: CameraAccessException) {
//            Log.d(TAG, "flash off fail : " + e.localizedMessage)
//        }

    }

    private fun getCameraId(): String? {
        val cameraIds = cameraManager.cameraIdList
        for (id in cameraIds) {
            val info = cameraManager.getCameraCharacteristics(id)
            val flashAvailable = info.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
            val lensFacing = info.get(CameraCharacteristics.LENS_FACING)
            if (flashAvailable != null
                    && flashAvailable
                    && lensFacing != null
                    && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                return id
            }
        }
        return null
    }
}