package com.example.myapp

import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = Session.currentUser(this)
        if (user == null) {
            Session.redirectToLogin(this)
            return
        }

        setContentView(R.layout.activity_settings)

        val db = DatabaseHandler.getInstance(this)
        val switchNightMode = findViewById<Switch>(R.id.switchNightMode)

        switchNightMode.isChecked = user.nightMode

        switchNightMode.setOnCheckedChangeListener { _, isChecked ->
            db.setNightMode(user.id, isChecked)
            ThemeManager.apply(isChecked)
        }
    }
}