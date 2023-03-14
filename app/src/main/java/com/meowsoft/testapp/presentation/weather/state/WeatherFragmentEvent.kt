package com.meowsoft.testapp.presentation.weather.state

import com.meowsoft.testapp.domain.location.model.ForecastLocation

sealed class WeatherFragmentEvent {
    data class ArgumentsObtained(val forecastLocation: ForecastLocation) : WeatherFragmentEvent()
}
