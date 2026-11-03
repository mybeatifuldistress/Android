package com.example.myapp

object Avatars {
    val ids = listOf(
        R.drawable.avatar1,
        R.drawable.avatar2,
        R.drawable.avatar3,
        R.drawable.avatar4,
    )

    fun res(index: Int): Int = ids.getOrElse(index) {R.drawable.ic_launcher_foreground}

    fun random(): Int = ids.indices.random()
}