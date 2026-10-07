package com.example.myapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class RegistrationActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "RegistrationActivity"

        private const val FIRST_NAME = "first_name"
        private const val LAST_NAME = "last_name"
        private const val EMAIL = "email"
        private const val PHONE = "phone"
        private const val LOGIN = "registration_login"
        private const val PASSWORD = "registration_password"
    }

    private lateinit var editFirstName: EditText
    private lateinit var editLastName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPhone: EditText
    private lateinit var editLogin: EditText
    private lateinit var editPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i(TAG, "RegistrationActivity: onCreate")

        setContentView(R.layout.activity_registration)

        editFirstName = findViewById<EditText>(R.id.editFirstname)
        editLastName = findViewById<EditText>(R.id.editLastname)
        editEmail = findViewById<EditText>(R.id.editEmail)
        editPhone = findViewById<EditText>(R.id.editPhone)
        editLogin = findViewById<EditText>(R.id.editRegistrationLogin)
        editPassword = findViewById<EditText>(R.id.editRegistrationPassword)

        if (savedInstanceState != null) {
            editFirstName.setText(savedInstanceState.getString(FIRST_NAME, ""))

            editLastName.setText(savedInstanceState.getString(LAST_NAME, ""))

            editEmail.setText(savedInstanceState.getString(EMAIL, ""))

            editPhone.setText(savedInstanceState.getString(PHONE, ""))

            editLogin.setText(savedInstanceState.getString(LOGIN, ""))

            editPassword.setText(savedInstanceState.getString(PASSWORD, ""))
        }

        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        buttonRegister.setOnClickListener {
            Log.i(TAG, "Нажата кнопка регистрации")
            val intent = Intent(this, MainActivity::class.java)

            intent.putExtra("first_name", editFirstName.text.toString())
            intent.putExtra("last_name", editLastName.text.toString())
            intent.putExtra("email", editEmail.text.toString())
            intent.putExtra("phone", editPhone.text.toString())

            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        Log.i(TAG, "RegistrationActivity: onStart")
    }

    override fun onResume() {
        super.onResume()

        Log.i(TAG, "RegistrationActivity: onResume")
    }

    override fun onPause() {
        super.onPause()

        Log.i(TAG, "RegistrationActivity: onPause")
    }

    override fun onStop() {
        super.onStop()

        Log.i(TAG, "RegistrationActivity: onStop")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.i(TAG, "RegistrationActivity: onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {

        outState.putString(FIRST_NAME, editFirstName.text.toString())

        outState.putString(LAST_NAME, editLastName.text.toString())

        outState.putString(EMAIL, editEmail.text.toString())

        outState.putString(PHONE, editPhone.text.toString())

        outState.putString(LOGIN, editLogin.text.toString())

        outState.putString(PASSWORD, editPassword.text.toString())

        Log.i(TAG, "RegistrationActivity: onSaveInstanceState")

        super.onSaveInstanceState(outState)
    }
}