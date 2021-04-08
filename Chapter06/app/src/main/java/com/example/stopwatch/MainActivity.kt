package com.example.stopwatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.stopwatch.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var time = 0
    private var isRunning = false
    private var timerTask: Timer? = null
    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.fab.setOnClickListener {
            isRunning = !isRunning

            if (isRunning) {
                start()
            } else {
                pause()
            }
        }

        binding.resetFab.setOnClickListener {
            reset()
        }

        binding.lapButton.setOnClickListener {
            recordLapTime()
        }

    }

    private fun pause() {
        binding.fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)

        timerTask?.cancel()
    }

    private fun start() {
        binding.fab.setImageResource(R.drawable.ic_pause_black_24dp)

        timerTask = timer(period = 10) {
            time++
            val sec = time / 100
            val milli = time % 100
            runOnUiThread {
                if (isRunning) {
                    binding.secTextView.text = "$sec"
                    binding.milliTextView.text = "$milli"
                }
            }
        }
    }

    private fun reset() {
        timerTask?.cancel()

        // 모든 변수 초기화
        time = 0
        isRunning = false

        binding.fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        binding.secTextView.text = "0"
        binding.milliTextView.text = "00"

        // 모든 랩타임을 제거
        binding.lapLayout.removeAllViews()
        lap = 1
    }

    private fun recordLapTime() {
        val lapTime = this.time
        val textView = TextView(this)
        textView.text = "$lap LAP : ${lapTime / 100}.${lapTime % 100}"

        // 맨 위에 랩타임 추가
        binding.lapLayout.addView(textView, 0)
        lap++
    }

}
