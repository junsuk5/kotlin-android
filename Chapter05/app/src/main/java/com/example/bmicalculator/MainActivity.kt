package com.example.bmicalculator

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.bmicalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 이전에 입력한 값을 읽어오기
        loadData()

        binding.resultButton.setOnClickListener {
            if (TextUtils.isEmpty(binding.weightEditText.text) ||
                    TextUtils.isEmpty(binding.heightEditText.text)) {
                Toast.makeText(this, "값을 입력해 주세요 please", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 마지막에 입력한 내용을 저장
            saveData(binding.heightEditText.text.toString().toInt(),
                    binding.weightEditText.text.toString().toInt())

            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtra("weight", binding.weightEditText.text.toString())
                putExtra("height", binding.heightEditText.text.toString())
            }
            startActivity(intent)
        }
    }

    private fun saveData(height: Int, weight: Int) {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()

        editor.putInt("KEY_HEIGHT", height)
                .putInt("KEY_WEIGHT", weight)
                .apply()
    }

    private fun loadData() {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val height = pref.getInt("KEY_HEIGHT", 0)
        val weight = pref.getInt("KEY_WEIGHT", 0)

        if (height != 0 && weight != 0) {
            binding.heightEditText.setText(height.toString())
            binding.weightEditText.setText(weight.toString())
        }
    }
}