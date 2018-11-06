package com.anwesh.uiprojects.crossrotatestepview

/**
 * Created by anweshmishra on 06/11/18.
 */

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color
import android.content.Context
import android.app.Activity
import android.util.Log
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
    paint.strokeWidth = Math.min(w, h) / 60
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

class CrossRotateStepView(ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var prevScale : Float = 0f, var dir : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            val k : Float = scale.updateScale(dir)
            Log.d("scale factor", "$k")
            scale += k
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }

    data class Animator(var view : View, var animated : Boolean = false) {

        fun animate(cb : () -> Unit) {
            if (animated) {
                cb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                } catch(ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class CRSNode(var i : Int, val state : State = State()) {

        private var next : CRSNode? = null

        private var prev : CRSNode? = null

        init {
            addNeighbor()
        }

        fun addNeighbor() {
            if (i < nodes - 1) {
                next = CRSNode(i + 1)
                next?.prev = this
            }
        }

        fun draw(canvas : Canvas, paint : Paint) {
            canvas.drawCRSNode(i, state.scale, paint)
            next?.draw(canvas, paint)
        }

        fun update(cb : (Int, Float) -> Unit) {
            state.update {
                cb(i, it)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            state.startUpdating(cb)
        }

        fun getNext(dir : Int, cb : () -> Unit) : CRSNode {
            var curr : CRSNode? = prev
            if (dir == 1) {
                curr = next
            }
            if (curr != null) {
                return curr
            }
            cb()
            return this
        }
    }
}