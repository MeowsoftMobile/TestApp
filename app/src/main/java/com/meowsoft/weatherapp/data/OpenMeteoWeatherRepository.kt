package com.meowsoft.weatherapp.data

import com.meowsoft.weatherapp.data.mappers.toForecast
import com.meowsoft.weatherapp.data.remote.OpenMeteoAPI
import com.meowsoft.weatherapp.domain.common.Result
import com.meowsoft.weatherapp.domain.weather.WeatherRepository
import com.meowsoft.weatherapp.domain.weather.model.Forecast
import javax.inject.Inject

class OpenMeteoWeatherRepository @Inject constructor(
    private val openMeteoAPI: OpenMeteoAPI
) : WeatherRepository {
    override suspend fun getForecast(lat: Double, long: Double): Result<Forecast> {
        return try {
            val result = openMeteoAPI.getForecast(lat, long)
            Result.Success(
                result.weatherData.toForecast()
            )
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown request error in getForecast()")
        }
    }
}
