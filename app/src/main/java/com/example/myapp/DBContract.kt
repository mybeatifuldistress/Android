package com.example.myapp

import android.provider.BaseColumns

object DBContract {
    object UserEntry: BaseColumns {
        const val TABLE_NAME = "users"

        const val COLUMN_ID = "id"
        const val COLUMN_LOGIN = "login"
        const val COLUMN_PASS_HASH = "pass_hash"
        const val COLUMN_FIRST_NAME = "first_name"
        const val COLUMN_LAST_NAME = "last_name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PHONE = "phone"
        const val COLUMN_BIRTH_DATE = "birth_date"
        const val COLUMN_GENDER = "gender"
        const val COLUMN_AVATAR = "avatar"
        const val COLUMN_IS_ADMIN = "is_admin"
        const val COLUMN_NIGHT_MODE = "night_mode"
        const val COLUMN_TIME = "time"
    }
}