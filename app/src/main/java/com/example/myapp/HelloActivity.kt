package com.example.myapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HelloActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_helloact)

        val button1 = findViewById<Button>(R.id.button1)

        button1.setOnClickListener {
            button1.text = "Нажато!"
        }
    }
}