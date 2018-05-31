package com.example.tiltsensor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.SensorEvent
import android.view.View

class TiltView(context: Context) : View(context) {

    private val paint: Paint = Paint()
    private val blackPaint: Paint
    private var xCoord: Float = 0.toFloat()
    private var yCoord: Float = 0.toFloat()
    private var centerX: Float = 0f
    private var centerY: Float = 0f

    init {
        paint.color = Color.GREEN

        blackPaint = Paint()
        blackPaint.color = Color.BLACK
        blackPaint.style = Paint.Style.STROKE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2f
        centerY = h / 2f
    }

    fun onSensorEvent(event: SensorEvent) {
        // 화면을 가로로 돌렸으므로 X축과 Y축을 서로 바꿈
        yCoord = event.values[0] * 20
        xCoord = event.values[1] * 20

        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        // 바깥 원
        canvas.drawCircle(centerX, centerY, 100f, blackPaint)
        // 녹색 원
        canvas.drawCircle(xCoord + centerX, yCoord + centerY, 100f, paint)
        // 가운데 십자가
        canvas.drawLine(centerX - 20, centerY, centerX + 20, centerY, blackPaint)
        canvas.drawLine(centerX, centerY - 20, centerX, centerY + 20, blackPaint)
    }

}