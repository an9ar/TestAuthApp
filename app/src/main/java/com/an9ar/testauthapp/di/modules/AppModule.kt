package com.an9ar.testauthapp.di.modules

import android.content.Context
import com.an9ar.testauthapp.App
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideContext(app: App): Context = app

}