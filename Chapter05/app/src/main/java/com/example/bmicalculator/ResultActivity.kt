package com.example.bmicalculator

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bmicalculator.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 전달 받은 키와 몸무게
        val height = intent.getStringExtra("height")?.toInt() ?: 0
        val weight = intent.getStringExtra("weight")?.toInt() ?: 0

        // BMI 계산
        val bmi = weight / Math.pow(height / 100.0, 2.0)

        // 결과 표시
        when {
            bmi >= 35 -> binding.resultTextView.text = "고도 비만"
            bmi >= 30 -> binding.resultTextView.text = "2단계 비만"
            bmi >= 25 -> binding.resultTextView.text = "1단계 비만"
            bmi >= 23 -> binding.resultTextView.text = "과체중"
            bmi >= 18.5 -> binding.resultTextView.text = "정상"
            else -> binding.resultTextView.text = "저체중"
        }

        // 이미지 표시
        when {
            bmi >= 23 ->
                binding.imageView.setImageResource(
                        R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
            bmi >= 18.5 ->
                binding.imageView.setImageResource(
                        R.drawable.ic_sentiment_satisfied_black_24dp)
            else ->
                binding.imageView.setImageResource(
                        R.drawable.ic_sentiment_dissatisfied_black_24dp)
        }

        // 토스트 메시지로 BMI 값 표시
        Toast.makeText(this, "$bmi", Toast.LENGTH_SHORT).show()
    }
}

