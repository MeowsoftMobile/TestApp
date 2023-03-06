package com.meowsoft.testapp.data.di

import com.meowsoft.testapp.data.OpenMeteoWeatherRepository
import com.meowsoft.testapp.domain.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindsWeatherRepository(weatherRepository: OpenMeteoWeatherRepository): WeatherRepository
}
