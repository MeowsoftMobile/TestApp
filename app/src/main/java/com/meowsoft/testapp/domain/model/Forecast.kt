package com.meowsoft.testapp.domain.model

data class Forecast(
    val weatherPerDay: Map<Int, List<WeatherData>>,
    val currentWeather: WeatherData?
)
