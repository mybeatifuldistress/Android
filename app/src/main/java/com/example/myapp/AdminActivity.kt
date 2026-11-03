package com.example.myapp

import DatabaseHandler
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AdminActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHandler
    private lateinit var listUsers: ListView
    private var users = listOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewer = Session.currentUser(this)
        if (viewer == null) {
            Session.redirectToLogin(this)
            return
        }
        if (!viewer.isAdmin) {
            Toast.makeText(this, R.string.admin_only, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setContentView(R.layout.activity_admin)
        db = DatabaseHandler.getInstance(this)
        listUsers = findViewById(R.id.listUsers)
        val buttonAuthLog = findViewById<Button>(R.id.buttonAuthLog)

        buttonAuthLog.setOnClickListener {
            startActivity(Intent(this, AuthLogActivity::class.java))
        }

        listUsers.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, ProfileActivity::class.java)
                .putExtra(ProfileActivity.EXTRA_USER_ID, users[position].id)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        if (::listUsers.isInitialized) loadUsers()
    }

    private fun loadUsers() {
        users = db.getAllUsers()

        val data = users.map { user ->
            val role = getString(if (user.isAdmin) R.string.role_admin else R.string.role_user)
            mapOf(
                "name" to user.displayName,
                "avatar" to Avatars.res(user.avatar),
                "subtitle" to "@${user.login} • $role"
            )
        }

        val keys = arrayOf("name", "avatar", "subtitle")
        val ids = intArrayOf(R.id.itemContent, R.id.usersAvatar, R.id.itemSubtitle)

        listUsers.adapter = SimpleAdapter(this, data, R.layout.item, keys, ids)
    }
}