import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapp.DBContract.UserEntry
import com.example.myapp.PasswordHasher
import com.example.myapp.User

class DatabaseHandler private constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Lab5App.db"

        @Volatile
        private var INSTANCE: DatabaseHandler? = null

        fun getInstance(context: Context): DatabaseHandler =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DatabaseHandler(context.applicationContext).also { INSTANCE = it }
            }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE ${UserEntry.TABLE_NAME} (" +
                    "${UserEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${UserEntry.COLUMN_LOGIN} TEXT NOT NULL UNIQUE," +
                    "${UserEntry.COLUMN_PASS_HASH} TEXT NOT NULL," +
                    "${UserEntry.COLUMN_FIRST_NAME} TEXT NOT NULL," +
                    "${UserEntry.COLUMN_LAST_NAME} TEXT NOT NULL," +
                    "${UserEntry.COLUMN_EMAIL} TEXT NOT NULL," +
                    "${UserEntry.COLUMN_PHONE} TEXT NOT NULL," +
                    "${UserEntry.COLUMN_BIRTH_DATE} TEXT NOT NULL," +
                    "${UserEntry.COLUMN_GENDER} TEXT NOT NULL," +
                    "${UserEntry.COLUMN_AVATAR} INTEGER NOT NULL," +
                    "${UserEntry.COLUMN_IS_ADMIN} INTEGER NOT NULL DEFAULT 0," +
                    "${UserEntry.COLUMN_NIGHT_MODE} INTEGER NOT NULL DEFAULT 0," +
                    "${UserEntry.COLUMN_TIME} INTEGER NOT NULL)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${UserEntry.TABLE_NAME}")
        onCreate(db)
    }

    fun registerUser(user: User, password: String): User? {
        val db = writableDatabase
        db.beginTransaction()
        try {
            if (isLoginTaken(db, user.login)) return null

            val isFirstUser = DatabaseUtils.queryNumEntries(db, UserEntry.TABLE_NAME) == 0L

            val values = ContentValues().apply {
                put(UserEntry.COLUMN_LOGIN, user.login)
                put(UserEntry.COLUMN_PASS_HASH, PasswordHasher.hash(password))
                put(UserEntry.COLUMN_FIRST_NAME, user.firstName)
                put(UserEntry.COLUMN_LAST_NAME, user.lastName)
                put(UserEntry.COLUMN_EMAIL, user.email)
                put(UserEntry.COLUMN_PHONE, user.phone)
                put(UserEntry.COLUMN_BIRTH_DATE, user.birthDate)
                put(UserEntry.COLUMN_GENDER, user.gender)
                put(UserEntry.COLUMN_AVATAR, user.avatar)
                put(UserEntry.COLUMN_IS_ADMIN, if (isFirstUser) 1 else 0)
                put(UserEntry.COLUMN_NIGHT_MODE, if (user.nightMode) 1 else 0)
                put(UserEntry.COLUMN_TIME, user.time)
            }

            val id = db.insert(UserEntry.TABLE_NAME, null, values)
            if (id == -1L) return null

            db.setTransactionSuccessful()
            return user.copy(id = id, isAdmin = isFirstUser)
        } finally {
            db.endTransaction()
        }
    }

    fun authenticate(login: String, password: String): User? =
        readableDatabase.query(
            UserEntry.TABLE_NAME, null,
            "${UserEntry.COLUMN_LOGIN} = ?", arrayOf(login),
            null, null, null
        ).use { cursor ->
            if (cursor.moveToFirst()) {
                val storedHash = cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_PASS_HASH))
                if (PasswordHasher.verify(password, storedHash)) cursorToUser(cursor) else null
            } else {
                null
            }
        }

    fun getUserById(id: Long): User? =
        readableDatabase.query(
            UserEntry.TABLE_NAME, null,
            "${UserEntry.COLUMN_ID} = ?", arrayOf(id.toString()),
            null, null, null
        ).use { if (it.moveToFirst()) cursorToUser(it) else null }

    private fun cursorToUser(c: Cursor) = User(
        id = c.getLong(c.getColumnIndexOrThrow(UserEntry.COLUMN_ID)),
        login = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_LOGIN)),
        firstName = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_FIRST_NAME)),
        lastName = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_LAST_NAME)),
        email = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_EMAIL)),
        phone = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_PHONE)),
        birthDate = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_BIRTH_DATE)),
        gender = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_GENDER)),
        avatar = c.getInt(c.getColumnIndexOrThrow(UserEntry.COLUMN_AVATAR)),
        isAdmin = c.getInt(c.getColumnIndexOrThrow(UserEntry.COLUMN_IS_ADMIN)) == 1,
        nightMode = c.getInt(c.getColumnIndexOrThrow(UserEntry.COLUMN_NIGHT_MODE)) == 1,
        time = c.getLong(c.getColumnIndexOrThrow(UserEntry.COLUMN_TIME))
        )

    private fun isLoginTaken(db: SQLiteDatabase, login: String): Boolean =
        db.query(
            UserEntry.TABLE_NAME, arrayOf(UserEntry.COLUMN_ID),
            "${UserEntry.COLUMN_LOGIN} = ?", arrayOf(login),
            null, null, null
        ).use { it.moveToFirst() }

    fun getAllUsers(): List<User> {
        val result = mutableListOf<User>()
        readableDatabase.query(
            UserEntry.TABLE_NAME, null, null, null,
            null, null, "${UserEntry.COLUMN_ID} ASC"
        ).use { cursor ->
            while (cursor.moveToNext()) {
                result.add(cursorToUser(cursor))
            }
        }
        return result
    }

    fun setAdmin(userId: Long, isAdmin: Boolean) {
        val values = ContentValues().apply {
            put(UserEntry.COLUMN_IS_ADMIN, if (isAdmin) 1 else 0)
        }
        writableDatabase.update(
            UserEntry.TABLE_NAME, values,
            "${UserEntry.COLUMN_ID} = ?", arrayOf(userId.toString())
        )
    }

    fun setNightMode(userId: Long, nightMode: Boolean) {
        val values = ContentValues().apply {
            put(UserEntry.COLUMN_NIGHT_MODE, if (nightMode) 1 else 0)
        }
        writableDatabase.update(
            UserEntry.TABLE_NAME, values,
            "${UserEntry.COLUMN_ID} = ?", arrayOf(userId.toString())
        )
    }
}