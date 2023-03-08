package com.meowsoft.testapp.domain.model

import java.time.LocalDateTime

data class WeatherData(
    val time: LocalDateTime,
    val temperature: Double,
    val pressure: Double,
    val windSpeed: Double,
    val humidity: Double,
    val type: WeatherType
)
