package com.meowsoft.testapp.domain.weather

import com.meowsoft.testapp.domain.weather.model.Forecast

data class WeatherState(
    val forecast: Forecast? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
