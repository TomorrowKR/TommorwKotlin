package com.example.myapplication

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation

class CustomProgressAnimation(
    private val view: View,
    private val from: Float,
    private val to: Float
) : Animation() {
    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        val value = from + (to - from) * interpolatedTime
        when (view) {
            is CircularProgressView -> view.setProgress(value)
            is LinearProgressView -> view.setProgress(value)
            is ArcProgressView -> view.setProgress(value)
        }
    }
}