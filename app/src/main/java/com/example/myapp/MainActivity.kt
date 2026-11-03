package com.example.myapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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

    private val registrationLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val login = result.data?.getStringExtra("registered_login")
                if (login != null) {
                    editLogin.setText(login)
                    editPassword.text.clear()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        DatabaseHandler.getInstance(this).writableDatabase

        editLogin = findViewById(R.id.editLogin)
        editPassword = findViewById(R.id.editPassword)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonRegistration = findViewById<Button>(R.id.buttonRegistration)
        val buttonExit = findViewById<Button>(R.id.buttonExit)
        val switchTheme = findViewById<Switch>(R.id.switchTheme)

        switchTheme.isChecked = ThemeManager.isNightFromPrefs(this)
        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            ThemeManager.saveNightToPrefs(this, isChecked)
            ThemeManager.apply(isChecked)
        }

        if (savedInstanceState != null) {
            editLogin.setText(savedInstanceState.getString(LOGIN, ""))

            editPassword.setText(savedInstanceState.getString(PASSWORD, ""))
        }

        buttonLogin.setOnClickListener {
            val login = editLogin.text.toString().trim()
            val password = editPassword.text.toString()

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = DatabaseHandler.getInstance(this).authenticate(login, password)
            if (user == null) {
                AuthLogger.logFailure(this, login)
                Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AuthLogger.logSuccess(this, user)
            Session.login(user.id)
            ThemeManager.apply(user.nightMode)
            startActivity(Intent(this, MenuActivity::class.java))
        }

        buttonRegistration.setOnClickListener {
            registrationLauncher.launch(Intent(this, RegistrationActivity::class.java))
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

        if (isFinishing) {
            Session.logout()
        }

        Log.i(TAG, "MainActivity: onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {

        outState.putString(LOGIN, editLogin.text.toString())

        outState.putString(PASSWORD, editPassword.text.toString())

        Log.i(TAG, "MainActivity: onSaveInstanceState")

        super.onSaveInstanceState(outState)
    }
}