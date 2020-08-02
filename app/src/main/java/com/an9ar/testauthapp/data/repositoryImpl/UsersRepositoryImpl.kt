package com.an9ar.testauthapp.data.repositoryImpl

import android.annotation.SuppressLint
import android.util.Log
import com.an9ar.testauthapp.data.database.UserDao
import com.an9ar.testauthapp.data.database.UserEntity
import com.an9ar.testauthapp.domain.errors.AuthException
import com.an9ar.testauthapp.domain.repository.UsersRepository
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UsersRepository {

    override fun insertUser(user: UserEntity): Completable {
        return userDao.insert(user)
    }

    override fun getListOfUsers() : Single<List<UserEntity>>{
        return userDao.getUserList()
    }

    @SuppressLint("CheckResult")
    override fun checkUserForExistence(email: String, password: String): Completable =
        userDao.checkUserForExistence(email = email, password = password)
            .subscribeOn(Schedulers.io())
            .flatMapCompletable{ result ->
                Completable.error(AuthException.UserAlreadyExistsException)
            }
}