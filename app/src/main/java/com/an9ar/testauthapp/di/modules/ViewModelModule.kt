package com.an9ar.testauthapp.di.modules

import com.an9ar.testauthapp.domain.repository.UsersRepository
import com.an9ar.testauthapp.domain.repository.WeatherRepository
import com.an9ar.testauthapp.presentation.MainViewModel
import com.an9ar.testauthapp.domain.usecase.LoginUseCase
import com.an9ar.testauthapp.domain.usecase.RegisterUseCase
import com.an9ar.testauthapp.presentation.handlers.AuthErrorHandler
import com.an9ar.testauthapp.presentation.providers.ResourceProvider
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun provideMainViewModel(
        registerUseCase: RegisterUseCase,
        loginUseCase: LoginUseCase,
        errorHandler: AuthErrorHandler,
        usersRepository: UsersRepository,
        weatherRepository: WeatherRepository,
        resourceProvider: ResourceProvider
    ) = MainViewModel(
        registerUseCase = registerUseCase,
        loginUseCase = loginUseCase,
        errorHandler = errorHandler,
        usersRepository = usersRepository,
        weatherRepository = weatherRepository,
        resourceProvider = resourceProvider
    )
}