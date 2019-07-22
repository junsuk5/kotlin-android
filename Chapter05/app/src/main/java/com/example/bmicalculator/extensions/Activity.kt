package com.example.bmicalculator.extensions

import android.app.Activity
import android.widget.Toast

// 액티비티에서 간단히 토스트 메시지를 표시할 수 있는 확장 함수
fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}