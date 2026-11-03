package com.example.myapp

data class User(
    val id: Long = -1,
    val login: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val birthDate: String,
    val gender: String,
    val avatar: Int,
    val isAdmin: Boolean = false,
    val nightMode: Boolean = false,
    val time: Long = System.currentTimeMillis()
) {
    val displayName: String
        get() = "$firstName $lastName".trim().ifEmpty { login }
}
