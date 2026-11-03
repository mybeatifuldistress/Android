package com.example.myapp

import DatabaseHandler
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER_ID = "user_id"
    }

    private lateinit var db: DatabaseHandler
    private var targetId = Session.NO_USER

    private lateinit var buttonMakeAdmin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewer = Session.currentUser(this)
        if (viewer == null) {
            Session.redirectToLogin(this)
            return
        }

        setContentView(R.layout.activity_profile)
        db = DatabaseHandler.getInstance(this)
        buttonMakeAdmin = findViewById(R.id.buttonMakeAdmin)

        val requestedId = intent.getLongExtra(EXTRA_USER_ID, viewer.id)
        targetId = if (viewer.isAdmin) requestedId else viewer.id

        buttonMakeAdmin.setOnClickListener {
            val currentViewer = Session.currentUser(this)
            if (currentViewer != null && currentViewer.isAdmin) {
                db.setAdmin(targetId, true)
                val target = db.getUserById(targetId)
                if (target != null) {
                    Toast.makeText(
                        this, getString(R.string.now_admin, target.displayName), Toast.LENGTH_SHORT
                    ).show()
                }
                showUser()
            }
        }

        showUser()
    }

    private fun showUser() {
        val viewer = Session.currentUser(this)
        val target = db.getUserById(targetId)
        if (viewer == null || target == null) {
            finish()
            return
        }

        findViewById<ImageView>(R.id.imageProfileAvatar)
            .setImageResource(Avatars.res(target.avatar))
        findViewById<TextView>(R.id.textLogin).text = target.login
        findViewById<TextView>(R.id.textFirstName).text = target.firstName
        findViewById<TextView>(R.id.textLastName).text = target.lastName
        findViewById<TextView>(R.id.textEmail).text = target.email
        findViewById<TextView>(R.id.textPhone).text = target.phone
        findViewById<TextView>(R.id.textBirthDate).text = target.birthDate
        findViewById<TextView>(R.id.textGender).text = target.gender
        findViewById<TextView>(R.id.textRole).text =
            getString(if (target.isAdmin) R.string.role_admin else R.string.role_user)

        buttonMakeAdmin.visibility =
            if (viewer.isAdmin && !target.isAdmin) View.VISIBLE else View.GONE
    }
}