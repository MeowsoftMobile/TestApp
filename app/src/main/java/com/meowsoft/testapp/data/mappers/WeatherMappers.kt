package com.meowsoft.testapp.data.mappers

import com.meowsoft.testapp.data.remote.dto.WeatherDataDto
import com.meowsoft.testapp.domain.weather.model.Forecast
import com.meowsoft.testapp.domain.weather.model.WeatherData
import com.meowsoft.testapp.domain.weather.model.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.floor

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return List(time.size) { index ->
        IndexedValue(
            index,
            WeatherData(
                time = LocalDateTime.parse(time[index], DateTimeFormatter.ISO_DATE_TIME),
                temperature = temperatures[index],
                pressure = pressures[index],
                windSpeed = windSpeeds[index],
                humidity = humidities[index],
                type = WeatherType.fromWMO(weatherCodes[index])
            )
        )
    }.groupBy {
        floor(it.index.toDouble() / 24).toInt()
    }.mapValues { entry ->
        entry.value.map { it.value }
    }
}

fun WeatherDataDto.toForecast(): Forecast {
    val map = toWeatherDataMap()
    val now = LocalDateTime.now()

    val currentWeather = map[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }

    return Forecast(
        map,
        currentWeather
    )
}
