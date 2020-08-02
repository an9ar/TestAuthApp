package com.an9ar.testauthapp.domain.validator

import com.an9ar.testauthapp.domain.errors.AuthException
import io.reactivex.Completable
import java.util.regex.Pattern
import javax.inject.Inject

class PasswordValidator @Inject constructor() :
    Validator {

    companion object {
        const val REGEXP_PASSWORD = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})"
        const val PASSWORD_MIN_LENGTH = 6
    }

    override fun validate(value: String?): Completable {
        val result = when{
            value.isNullOrEmpty() -> AuthException.EmptyPasswordException
            value.length < PASSWORD_MIN_LENGTH -> AuthException.ShortPasswordException
            else -> AuthException.InvalidPasswordException.takeIf {
                Pattern.compile(REGEXP_PASSWORD)
                    .matcher(value)
                    .matches()
                    .not()
            }
        }
        return if (result == null) Completable.complete()
        else Completable.error(result)
    }

}