package com.an9ar.testauthapp.domain.usecase

import com.an9ar.testauthapp.domain.validator.LoginValidator
import com.an9ar.testauthapp.domain.validator.PasswordValidator
import io.reactivex.Completable
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginValidator: LoginValidator,
    private val passwordValidator: PasswordValidator
) {
    fun checkEmailValidation(email: String?) : Completable{
        return loginValidator.validate(email)
    }
    fun checkPasswordValidation(password: String?) : Completable{
        return passwordValidator.validate(password)
    }
}