package com.example.myapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity

class UsersActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "UsersActivity"
    }

    val avatars = listOf(R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4)

    val initialMap = listOf(mapOf("name" to "Ivan Ivanov", "avatar" to R.drawable.avatar3))

    var currData = initialMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_users)

        val editUserName = findViewById<EditText>(R.id.editUserName)

        val textList = findViewById<ListView>(R.id.textList)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)

        val keys = arrayOf("name", "avatar")
        val ids = intArrayOf(R.id.itemContent, R.id.usersAvatar)

        val adapter = SimpleAdapter(this, currData, R.layout.item, keys, ids)

        textList.adapter = adapter

        buttonAdd.setOnClickListener {
            val userName = editUserName.text.toString().trim()

            if (userName.isNotBlank()) {
                val randomAvatars = avatars.random()

                currData = currData + mapOf("name" to userName, "avatar" to randomAvatars)

                textList.adapter = SimpleAdapter(this, currData, R.layout.item, keys, ids)

                editUserName.text.clear()

                Log.i(TAG, "Добавлен новый пользователь: $userName")
            } else {
                editUserName.error = "Имя не может быть пустым"
                Log.e(TAG, "Введено пустое имя")
            }
        }
    }
}
