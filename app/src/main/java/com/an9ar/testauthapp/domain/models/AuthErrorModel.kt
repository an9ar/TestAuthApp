package com.an9ar.testauthapp.domain.models

import com.an9ar.testauthapp.presentation.enums.LoginFields

data class AuthErrorModel(
    val field: LoginFields? = null,
    val errorText: String
)