package com.an9ar.testauthapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM userentity")
    fun getUserList(): Single<List<UserEntity>>

    @Query("SELECT * FROM userentity WHERE email=:email AND password=:password")
    fun checkUserForExistence(email: String, password: String): Maybe<UserEntity>

    @Insert
    fun insert(user: UserEntity): Completable
}