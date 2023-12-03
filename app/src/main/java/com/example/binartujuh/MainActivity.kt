package com.example.binartujuh

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val FragmentManager = supportFragmentManager
        val FragmentTransaction = FragmentManager.beginTransaction()
    }
}