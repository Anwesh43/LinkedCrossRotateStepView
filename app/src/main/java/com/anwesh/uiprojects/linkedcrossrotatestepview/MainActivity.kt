package com.anwesh.uiprojects.linkedcrossrotatestepview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.anwesh.uiprojects.crossrotatestepview.CrossRotateStepView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CrossRotateStepView.create(this)
    }
}
