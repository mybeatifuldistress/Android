package com.example.myapp

import android.app.Activity
import android.os.Bundle

class HelloActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helloact)
    }
}