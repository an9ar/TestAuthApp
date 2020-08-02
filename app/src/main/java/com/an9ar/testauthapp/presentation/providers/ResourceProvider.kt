package com.an9ar.testauthapp.presentation.providers

import androidx.annotation.DimenRes
import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes resId: Int): String
    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String
    fun getDimen(@DimenRes resId: Int): Float
}