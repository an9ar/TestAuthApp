package com.an9ar.testauthapp.di.components

import com.an9ar.testauthapp.*
import com.an9ar.testauthapp.di.modules.*
import com.an9ar.testauthapp.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ViewModelModule::class,
    UtilsModule::class,
    DatabaseModule::class,
    RepositoryModule::class,
    NetworkModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: App): Builder
    }

    fun inject(activity: MainActivity)

}