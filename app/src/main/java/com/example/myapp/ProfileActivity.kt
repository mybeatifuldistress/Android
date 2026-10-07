package com.example.myapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)

        val textFirstName = findViewById<TextView>(R.id.textFirstName)
        val textLastName = findViewById<TextView>(R.id.textLastName)
        val textEmail = findViewById<TextView>(R.id.textEmail)
        val textPhone = findViewById<TextView>(R.id.textPhone)

        val firstName = intent.getStringExtra("first_name")
        val lastName = intent.getStringExtra("last_name")
        val email = intent.getStringExtra("email")
        val phone = intent.getStringExtra("phone")

        textFirstName.text = firstName
        textLastName.text = lastName
        textEmail.text = email
        textPhone.text = phone
    }
}