package com.an9ar.testauthapp.domain.errors

object AuthException {
    //Validation exceptions
    object InvalidLoginException : Exception()
    object EmptyLoginException : Exception()
    object InvalidPasswordException : Exception()
    object ShortPasswordException : Exception()
    object EmptyPasswordException : Exception()

    //Auth exceptions
    object UserNotFoundException : Exception()
    object UserAlreadyExistsException : Exception()
}