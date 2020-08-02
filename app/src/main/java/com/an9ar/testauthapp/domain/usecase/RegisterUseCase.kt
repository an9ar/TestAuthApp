package com.an9ar.testauthapp.domain.usecase

import com.an9ar.testauthapp.domain.repository.UsersRepository
import com.an9ar.testauthapp.domain.validator.LoginValidator
import com.an9ar.testauthapp.domain.validator.PasswordValidator
import io.reactivex.Completable
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val loginValidator: LoginValidator,
    private val passwordValidator: PasswordValidator,
    private val usersRepository: UsersRepository
) {
    fun checkEmailValidation(email: String?) : Completable {
        return loginValidator.validate(email)
    }
    fun checkPasswordValidation(password: String?) : Completable {
        return passwordValidator.validate(password)
    }
    fun checkExistingUser(email: String, password: String) : Completable {
        return usersRepository.checkUserForExistence(email = email, password = password)
    }
}