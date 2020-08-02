package com.an9ar.testauthapp.domain.repository

import com.an9ar.testauthapp.data.database.UserEntity
import io.reactivex.Completable
import io.reactivex.Single

interface UsersRepository {
    fun insertUser(user: UserEntity): Completable
    fun getListOfUsers(): Single<List<UserEntity>>
    fun checkUserForExistence(email: String, password: String): Completable
}