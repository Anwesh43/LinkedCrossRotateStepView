package com.anwesh.uiprojects.crossrotatestepview

/**
 * Created by anweshmishra on 06/11/18.
 */

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color
import android.content.Context
import android.app.Activity
import android.view.View
import android.view.MotionEvent

val nodes : Int = 5

val lines : Int = 4

val scGap : Float = 0.05f

fun Int.getInverse() : Float = (1f / this)

fun Float.getScaleFactor() : Float = Math.floor(this / 0.5).toFloat()

fun Float.getMirrorValue(a : Float, b : Float) : Float = this * a + (1 - this) * b

fun Float.updateScale(dir : Float) = scGap * dir * getScaleFactor().getMirrorValue(1f, lines.getInverse())

fun Float.divideScale(i : Int, n : Int) = Math.min(n.getInverse(), Math.max(0f, this - n.getInverse() * i)) * n

fun Canvas.drawCRSNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    val gap : Float = w / (nodes + 1)
    val size : Float = gap / 3
    val deg : Float = 360f / nodes
    val sc1 : Float = scale.divideScale(0, 2)
    val sc2 : Float = scale.divideScale(1, 2)
    paint.color = Color.parseColor("#4CAF50")
    save()
    translate(gap * (i + 1), h/2)
    rotate(deg * sc2)
    for (j in 0..(lines - 1)) {
        val sc : Float = sc1.divideScale(j, lines)
        save()
        rotate(deg * j)
        drawLine(size - size * sc, 0f, size, 0f, paint)
        restore()
    }
    restore()
}