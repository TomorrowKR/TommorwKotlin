package com.example.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomLadderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var ladder: Ladder = Ladder(emptyList())
    private var numberOfParticipants = 0
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        strokeWidth = 5f
    }

    fun setLadder(ladder: Ladder, numberOfParticipants: Int) {
        this.ladder = ladder
        this.numberOfParticipants = numberOfParticipants
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawLadder(canvas)
    }

    private fun drawLadder(canvas: Canvas) {
        val padding = 50f
        val availableWidth = width - padding * 2
        val columnWidth = availableWidth / (numberOfParticipants - 1).toFloat()
        val availableHeight = height - padding * 2

        // 세로선 그리기
        for (i in 0 until numberOfParticipants) {
            val x = padding + i * columnWidth
            canvas.drawLine(x, padding, x, padding + availableHeight, paint)
        }

        // 가로선 그리기
        ladder.lines.forEach { line ->
            val startX = padding + line.from * columnWidth
            val endX = padding + line.to * columnWidth
            val y = padding + line.position * (availableHeight / 100f)
            canvas.drawLine(startX, y, endX, y, paint)
        }
    }
}
