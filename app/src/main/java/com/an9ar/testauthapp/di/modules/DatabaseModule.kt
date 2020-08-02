package com.an9ar.testauthapp.di.modules

import android.content.Context
import androidx.room.Room
import com.an9ar.testauthapp.data.database.AppDatabase
import com.an9ar.testauthapp.data.database.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.getUserDao()

}