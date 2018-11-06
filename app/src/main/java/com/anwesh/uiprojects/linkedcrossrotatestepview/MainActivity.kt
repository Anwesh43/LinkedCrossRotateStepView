package com.anwesh.uiprojects.linkedcrossrotatestepview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.anwesh.uiprojects.crossrotatestepview.CrossRotateStepView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view : CrossRotateStepView = CrossRotateStepView.create(this)
        fullScreen()
        view.addOnAnimationCompleteListener({
            createToast("animation number $it completed")
        }) {
            createToast("animation number $it is reset")
        }
    }

    private fun createToast(txt : String) {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show()
    }
}

fun MainActivity.fullScreen() {
    supportActionBar?.hide()
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}