package com.example.myapp

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object AuthLogger {
    private const val FILE_NAME = "auth_log.txt"

    private fun file(context: Context) = File(context.applicationContext.filesDir, FILE_NAME)

    private fun now(): String =
        SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()).format(Date())

    fun logSuccess(context: Context, user: User) {
        val role = if (user.isAdmin) "администратор" else "пользователь"
        append(context, "ВХОД | ${clean(user.login)} (id=${user.id}, $role)")
    }

    fun logFailure(context: Context, login: String) {
        append(context, "ОШИБКА ВХОДА | ${clean(login)}")
    }

    @Synchronized
    private fun append(context: Context, message: String) {
        file(context).appendText("${now()} | $message\n", Charsets.UTF_8)
    }

    private fun clean(text: String) = text.replace('\n', ' ').replace('\r', ' ')

    @Synchronized
    fun read(context: Context): String {
        val f = file(context)
        return if (f.exists()) f.readText(Charsets.UTF_8) else ""
    }

    @Synchronized
    fun clear(context: Context) {
        file(context).writeText("", Charsets.UTF_8)
    }
}