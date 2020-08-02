package com.an9ar.testauthapp.di.modules

import com.an9ar.testauthapp.domain.repository.UsersRepository
import com.an9ar.testauthapp.data.repositoryImpl.UsersRepositoryImpl
import com.an9ar.testauthapp.domain.repository.WeatherRepository
import com.an9ar.testauthapp.data.repositoryImpl.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {
    @Binds
    fun provideUsersRepository(impl: UsersRepositoryImpl): UsersRepository
    @Binds
    fun provideWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository
}