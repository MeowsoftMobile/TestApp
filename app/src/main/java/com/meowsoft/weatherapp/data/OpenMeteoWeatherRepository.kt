package com.meowsoft.weatherapp.data

import com.meowsoft.weatherapp.data.mappers.toForecast
import com.meowsoft.weatherapp.data.remote.OpenMeteoAPI
import com.meowsoft.weatherapp.domain.common.Request
import com.meowsoft.weatherapp.domain.weather.WeatherRepository
import com.meowsoft.weatherapp.domain.weather.model.Forecast
import javax.inject.Inject

class OpenMeteoWeatherRepository @Inject constructor(
    private val openMeteoAPI: OpenMeteoAPI
) : WeatherRepository {
    override suspend fun getForecast(lat: Double, long: Double): Request<Forecast> {
        return try {
            val result = openMeteoAPI.getForecast(lat, long)
            Request.Success(
                result.weatherData.toForecast()
            )
        } catch (e: Exception) {
            Request.Error(e.message ?: "Unknown request error in getForecast()")
        }
    }
}
