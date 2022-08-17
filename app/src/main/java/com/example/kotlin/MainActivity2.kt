package com.example.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toolbar

class MainActivity2 : AppCompatActivity() {
    lateinit var  toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
    }

    private fun setSupportActionBar(toolbar: Toolbar) {

    }

    fun showIntroAgain(v: View)
    {
        PrefManager(this).clearPreference()
    }
}