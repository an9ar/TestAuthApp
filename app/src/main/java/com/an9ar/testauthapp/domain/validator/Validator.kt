package com.an9ar.testauthapp.domain.validator

import io.reactivex.Completable

interface Validator {
    fun validate(value: String?): Completable = Completable.complete()
}