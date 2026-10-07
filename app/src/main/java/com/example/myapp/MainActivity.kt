package com.example.myapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"

        private const val LOGIN = "login"
        private const val PASSWORD = "password"
    }

    private lateinit var editLogin: EditText
    private lateinit var editPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editLogin = findViewById(R.id.editLogin)
        editPassword = findViewById(R.id.editPassword)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonRegistration = findViewById<Button>(R.id.buttonRegistration)
        val buttonExit = findViewById<Button>(R.id.buttonExit)

        if (savedInstanceState != null) {
            editLogin.setText(savedInstanceState.getString(LOGIN, ""))

            editPassword.setText(savedInstanceState.getString(PASSWORD, ""))
        }

        buttonLogin.setOnClickListener {
            if (editLogin.text.isNullOrEmpty() || editPassword.text.isNullOrEmpty()) {
                Log.i(TAG, "Необходимо ввести логин и пароль")
            } else {
                val intentMenu = Intent(this, MenuActivity::class.java)

                intentMenu.putExtra("first_name", intent.getStringExtra("first_name"))
                intentMenu.putExtra("last_name", intent.getStringExtra("last_name"))
                intentMenu.putExtra("email", intent.getStringExtra("email"))
                intentMenu.putExtra("phone", intent.getStringExtra("phone"))

                startActivity(intentMenu)
            }
        }

        buttonRegistration.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        buttonExit.setOnClickListener {
            finishAffinity()
        }
    }

    override fun onStart() {
        super.onStart()

        Log.i(TAG, "MainActivity: onStart")
    }

    override fun onResume() {
        super.onResume()

        Log.i(TAG, "MainActivity: onResume")
    }

    override fun onPause() {
        super.onPause()

        Log.i(TAG, "MainActivity: onPause")
    }

    override fun onStop() {
        super.onStop()

        Log.i(TAG, "MainActivity: onStop")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.i(TAG, "MainActivity: onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {

        outState.putString(LOGIN, editLogin.text.toString())

        outState.putString(PASSWORD, editPassword.text.toString())

        Log.i(TAG, "MainActivity: onSaveInstanceState")

        super.onSaveInstanceState(outState)
    }
}