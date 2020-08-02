package com.an9ar.testauthapp.di.modules

import com.an9ar.testauthapp.presentation.providers.ResourceProvider
import com.an9ar.testauthapp.presentation.providers.ResourceProviderImpl
import dagger.Binds
import dagger.Module

@Module
abstract class UtilsModule {
    @Binds
    abstract fun provideResourceProvider(impl: ResourceProviderImpl): ResourceProvider
}