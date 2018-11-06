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

fun Float.divideScale(i : Int, n : Int) = Math.min(n.getInverse(), Math.max(0f, this - n.getInverse() * i))