package com.meowsoft.testapp.presentation.weather.state

import com.meowsoft.testapp.domain.weather.model.Forecast

sealed class WeatherState(
    val forecast: Forecast? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    object Default : WeatherState(forecast = null, isLoading = false, error = null)
    object LoadingForecast : WeatherState(forecast = null, isLoading = true, error = null)
    class ForecastObtained(forecast: Forecast) : WeatherState(forecast = forecast, isLoading = false, error = null)
    class Error(message: String) : WeatherState(forecast = null, isLoading = false, error = message)
}
