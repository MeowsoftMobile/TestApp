package com.meowsoft.weatherapp.data.di

import com.meowsoft.weatherapp.data.OpenMeteoWeatherRepository
import com.meowsoft.weatherapp.data.location.LocationTrackerImpl
import com.meowsoft.weatherapp.domain.location.LocationTracker
import com.meowsoft.weatherapp.domain.weather.WeatherRepository
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

    @Binds
    @Singleton
    abstract fun bindsLocationTracker(locationTracker: LocationTrackerImpl): LocationTracker
}
