package com.an9ar.testauthapp.presentation.providers

import android.content.Context
import androidx.annotation.StringRes
import com.an9ar.testauthapp.presentation.providers.ResourceProvider
import javax.inject.Inject

class ResourceProviderImpl @Inject constructor(
    private val context: Context
) : ResourceProvider {
    override fun getDimen(resId: Int): Float = context.resources.getDimension(resId)
    override fun getString(@StringRes resId: Int): String = context.getString(resId)
    override fun getString(@StringRes resId: Int, vararg formatArgs: Any): String = context.getString(resId, *formatArgs)
}