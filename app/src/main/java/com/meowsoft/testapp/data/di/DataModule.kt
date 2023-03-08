package com.meowsoft.testapp.data.di

import com.meowsoft.testapp.data.OpenMeteoWeatherRepository
import com.meowsoft.testapp.data.location.LocationTrackerImpl
import com.meowsoft.testapp.domain.weather.WeatherRepository
import com.meowsoft.testapp.domain.location.LocationTracker
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
