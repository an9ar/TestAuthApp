package com.an9ar.testauthapp

import android.app.Application
import com.an9ar.testauthapp.di.components.AppComponent
import com.an9ar.testauthapp.di.components.DaggerAppComponent

class App : Application() {
    companion object {
        lateinit var appComponent: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).build()
    }
}