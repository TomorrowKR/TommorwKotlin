package com.example.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random

class CustomRouletteView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var sections: List<String> = emptyList()
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 48f
        color = Color.BLACK
        textAlign = Paint.Align.CENTER
    }
    private var bgPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    fun setSections(sections: List<String>) {
        this.sections = sections
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (sections.isEmpty()) return
        val angle = 360f / sections.size
        val radius = min(width, height) / 2f
        val centerX = width / 2f
        val centerY = height / 2f
        for (i in sections.indices) {
            bgPaint.color = getRandomColor()
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                i * angle,
                angle,
                true,
                bgPaint
            )
            val x =
                centerX + (radius / 1.5f) * cos(Math.toRadians((i * angle + angle / 2).toDouble())).toFloat()
            val y =
                centerY + (radius / 1.5f) * sin(Math.toRadians((i * angle + angle / 2).toDouble())).toFloat()
            canvas.drawText(sections[i], x, y, paint)
        }
    }

    private fun getRandomColor(): Int {
        val random = Random.Default
        return Color.argb(
            255, random.nextInt(256), random.nextInt(256),
            random.nextInt(256)
        )
    }
}
