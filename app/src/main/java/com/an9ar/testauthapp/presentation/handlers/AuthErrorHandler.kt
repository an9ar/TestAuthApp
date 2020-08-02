package com.an9ar.testauthapp.presentation.handlers

import com.an9ar.testauthapp.presentation.enums.LoginFields
import com.an9ar.testauthapp.R
import com.an9ar.testauthapp.domain.models.AuthErrorModel
import com.an9ar.testauthapp.domain.errors.AuthException
import com.an9ar.testauthapp.presentation.providers.ResourceProvider
import java.net.UnknownHostException
import javax.inject.Inject

class AuthErrorHandler @Inject constructor(
    private val resourceProvider: ResourceProvider
) {
    fun handle(error: Throwable): AuthErrorModel? {
        return when (error){
            is AuthException.EmptyLoginException -> AuthErrorModel(
                field = LoginFields.EMAIL,
                errorText = resourceProvider.getString(R.string.error_empty_field)
            )
            is AuthException.EmptyPasswordException -> AuthErrorModel(
                field = LoginFields.PASSWORD,
                errorText = resourceProvider.getString(R.string.error_empty_field)
            )
            is AuthException.InvalidLoginException -> AuthErrorModel(
                field = LoginFields.EMAIL,
                errorText = resourceProvider.getString(R.string.error_wrong_email)
            )
            is AuthException.InvalidPasswordException -> AuthErrorModel(
                field = LoginFields.PASSWORD,
                errorText = resourceProvider.getString(R.string.error_wrong_password)
            )
            is AuthException.ShortPasswordException -> AuthErrorModel(
                field = LoginFields.PASSWORD,
                errorText = resourceProvider.getString(R.string.error_short_password)
            )
            is AuthException.UserAlreadyExistsException -> AuthErrorModel(
                errorText = resourceProvider.getString(R.string.error_user_already_exists)
            )
            is UnknownHostException -> AuthErrorModel(
                errorText = resourceProvider.getString(R.string.error_no_internet)
            )
            else -> null
        }
    }
}