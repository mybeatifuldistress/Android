package com.example.myapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class AuthLogActivity : AppCompatActivity() {

    private lateinit var textLog: TextView

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

        setContentView(R.layout.activity_auth_log)

        textLog = findViewById(R.id.textLog)
        val buttonRefresh = findViewById<Button>(R.id.buttonRefreshLog)
        val buttonClear = findViewById<Button>(R.id.buttonClearLog)

        buttonRefresh.setOnClickListener { showLog() }

        buttonClear.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(R.string.confirm_clear_log)
                .setPositiveButton(R.string.yes) { _, _ ->
                    AuthLogger.clear(this)
                    showLog()
                    Toast.makeText(this, R.string.log_cleared, Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }

        showLog()
    }

    private fun showLog() {
        val lines = AuthLogger.read(this).lines().filter { it.isNotBlank() }.reversed()
        textLog.text = if (lines.isEmpty()) getString(R.string.log_empty) else lines.joinToString("\n")
    }
}