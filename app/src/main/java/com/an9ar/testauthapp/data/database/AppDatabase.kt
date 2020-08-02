package com.an9ar.testauthapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserEntity::class
    ],
    version = 5
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "USERS_DB"
    }

    abstract fun getUserDao(): UserDao
}