package com.example.myapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
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
        private const val BIRTH_DATE = "birth_date"
        private const val GENDER_ID = "gender_id"
    }

    private lateinit var editFirstName: EditText
    private lateinit var editLastName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPhone: EditText
    private lateinit var editLogin: EditText
    private lateinit var editPassword: EditText
    private lateinit var editBirthDate: EditText
    private lateinit var editGender: RadioGroup

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
        editBirthDate = findViewById<EditText>(R.id.editBirthDate)
        editGender = findViewById<RadioGroup>(R.id.radioGender)

        if (savedInstanceState != null) {
            editFirstName.setText(savedInstanceState.getString(FIRST_NAME, ""))

            editLastName.setText(savedInstanceState.getString(LAST_NAME, ""))

            editEmail.setText(savedInstanceState.getString(EMAIL, ""))

            editPhone.setText(savedInstanceState.getString(PHONE, ""))

            editLogin.setText(savedInstanceState.getString(LOGIN, ""))

            editPassword.setText(savedInstanceState.getString(PASSWORD, ""))

            editBirthDate.setText(savedInstanceState.getString(BIRTH_DATE, ""))

            val checkedGenderId = savedInstanceState.getInt(GENDER_ID, -1)
            if (checkedGenderId != -1) {
                editGender.check(checkedGenderId)
            }
        }

        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        val buttonAvatar = findViewById<Button>(R.id.buttonAvatar)

        buttonAvatar.setOnClickListener {
            Log.i(TAG, "Нажата кнопка выбора аватара")
        }

        buttonRegister.setOnClickListener {
            val firstName = editFirstName.text.toString().trim()
            val lastName = editLastName.text.toString().trim()
            val login = editLogin.text.toString().trim()
            val password = editPassword.text.toString()

            if (firstName.isEmpty()) { editFirstName.error = "Обязательное поле"; return@setOnClickListener }
            if (lastName.isEmpty()) { editLastName.error = "Обязательное поле"; return@setOnClickListener }
            if (login.isEmpty()) { editLogin.error = "Обязательное поле"; return@setOnClickListener }
            if (password.length < 4) { editPassword.error = "Не короче 4 символов"; return@setOnClickListener }

            val gender = when (editGender.checkedRadioButtonId) {
                R.id.radioMale -> "Мужской"
                R.id.radioFemale -> "Женский"
                else -> ""
            }

            val newUser = User(
                login = login,
                firstName = firstName,
                lastName = lastName,
                email = editEmail.text.toString().trim(),
                phone = editPhone.text.toString().trim(),
                birthDate = editBirthDate.text.toString().trim(),
                gender = gender,
                avatar = Avatars.random(),
                nightMode = ThemeManager.isNightFromPrefs(this)
            )

            val created = DatabaseHandler.getInstance(this).registerUser(newUser, password)
            if (created == null) {
                editLogin.error = "Такой логин уже занят"
                return@setOnClickListener
            }

            Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK, Intent().putExtra("registered_login", login))
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

        outState.putString(BIRTH_DATE, editBirthDate.text.toString())

        outState.putInt(GENDER_ID, editGender.checkedRadioButtonId)

        Log.i(TAG, "RegistrationActivity: onSaveInstanceState")

        super.onSaveInstanceState(outState)
    }
}