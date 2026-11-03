package com.example.myapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MenuActivity"
    }

    private lateinit var buttonAdmin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i(TAG, "MenuActivity: onCreate")

        setContentView(R.layout.activity_menu)

        val buttonStart = findViewById<Button>(R.id.buttonStart)
        val buttonProfile = findViewById<Button>(R.id.buttonProfile)
        val buttonSettings = findViewById<Button>(R.id.buttonSettings)
        val buttonProblem = findViewById<Button>(R.id.buttonProblem)
        val buttonExit = findViewById<Button>(R.id.buttonExit)

        buttonAdmin = findViewById(R.id.buttonAdmin)

        buttonProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        buttonAdmin.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
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

    override fun onResume() {
        super.onResume()

        val user = Session.currentUser(this)
        if (user == null) {
            Session.redirectToLogin(this)
            return
        }
        buttonAdmin.visibility = if (user.isAdmin) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            Session.logout()
            ThemeManager.applyFromPrefs(this)
        }
    }
}