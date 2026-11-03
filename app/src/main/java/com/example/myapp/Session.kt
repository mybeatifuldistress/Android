package com.example.myapp

import android.app.Activity
import android.content.Context
import android.content.Intent

object Session {
    const val NO_USER = -1L

    var currentUserId: Long = NO_USER
        private set // узнать ID можно из любого места приложения,
    // но изменять её можно только внутри самого объекта

    val isLoggedIn: Boolean get() = currentUserId != NO_USER

    fun login(userId: Long) {
        currentUserId = userId
    }

    fun logout() {
        currentUserId = NO_USER
    }

    fun currentUser(context: Context): User? =
        if (isLoggedIn) DatabaseHandler.getInstance(context).getUserById(currentUserId) else null

    fun redirectToLogin(activity: Activity) {
        val intent = Intent(activity, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        activity.startActivity(intent)
        activity.finish()
    }
}