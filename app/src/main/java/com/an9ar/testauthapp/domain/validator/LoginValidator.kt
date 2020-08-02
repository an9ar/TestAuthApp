package com.an9ar.testauthapp.domain.validator

import com.an9ar.testauthapp.domain.errors.AuthException
import io.reactivex.Completable
import java.util.regex.Pattern
import javax.inject.Inject

class LoginValidator @Inject constructor() :
    Validator {

    companion object {
        private const val REGEX_EMAIL = "^[a-zA-Z0-9_-]+(?:\\." +
                "[a-zA-Z0-9_-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$"
    }

    override fun validate(value: String?): Completable {
        val result = when{
            value.isNullOrEmpty() -> AuthException.EmptyLoginException
            else -> AuthException.InvalidLoginException.takeIf {
                Pattern.compile(REGEX_EMAIL)
                    .matcher(value)
                    .matches()
                    .not()
            }
        }
        return if (result == null) Completable.complete()
        else Completable.error(result)
    }
}