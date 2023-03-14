package com.meowsoft.weatherapp.presentation.weather.state

import com.meowsoft.weatherapp.domain.location.model.ForecastLocation

sealed class WeatherFragmentEvent {
    data class ArgumentsObtained(val forecastLocation: ForecastLocation) : WeatherFragmentEvent()
}
