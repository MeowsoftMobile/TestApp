package com.meowsoft.weatherapp.domain.weather.model

import java.time.LocalTime

data class Forecast(
    val weatherPerDay: Map<Int, List<WeatherData>>,
    val currentWeather: WeatherData?
) {
    fun weatherTypeOfTheDay(day: Int) = weatherPerDay[day]?.get(13)?.type ?: WeatherType.ClearSky
    fun averageTemperatureForDay(day: Int) = averageDailyTemperatures[day]

    val averageDailyTemperatures: List<Double>
        get() = weatherPerDay.map { entry ->
            entry
                .value
                .filter { isDay(it.time.toLocalTime()) }
                .sumOf {
                    it.temperature
                }
                .div(entry.value.size)
        }

    private fun isDay(time: LocalTime) = time.isAfter(dayStart) && time.isBefore(dayEnd)

    private val dayStart = LocalTime.of(6, 0)
    private val dayEnd = LocalTime.of(18, 0)
}
