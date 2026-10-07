package com.example.myapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MenuActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i(TAG, "MenuActivity: onCreate")

        setContentView(R.layout.activity_menu)

        val buttonStart = findViewById<Button>(R.id.buttonStart)
        val buttonProfile = findViewById<Button>(R.id.buttonProfile)
        val buttonSettings = findViewById<Button>(R.id.buttonSettings)
        val buttonProblem = findViewById<Button>(R.id.buttonProblem)
        val buttonExit = findViewById<Button>(R.id.buttonExit)

        buttonStart.setOnClickListener {  }

        buttonProfile.setOnClickListener {
            val intentProfile = Intent(this, ProfileActivity::class.java)

            intentProfile.putExtra("first_name", intent.getStringExtra("first_name"))
            intentProfile.putExtra("last_name", intent.getStringExtra("last_name"))
            intentProfile.putExtra("email", intent.getStringExtra("email"))
            intentProfile.putExtra("phone", intent.getStringExtra("phone"))

            startActivity(intentProfile)
        }

        buttonSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        buttonProblem.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_SUBJECT, "Сообщить о проблеме")
            }

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Log.e(TAG, "Почтовое приложение не обнаружено")
            }
        }

        buttonExit.setOnClickListener {
            finishAffinity()
        }
    }
}