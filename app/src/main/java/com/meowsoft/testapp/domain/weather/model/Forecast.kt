package com.meowsoft.testapp.domain.weather.model

data class Forecast(
    val weatherPerDay: Map<Int, List<WeatherData>>,
    val currentWeather: WeatherData?
)
