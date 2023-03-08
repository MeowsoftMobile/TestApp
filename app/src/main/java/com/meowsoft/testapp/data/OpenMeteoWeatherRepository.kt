package com.meowsoft.testapp.data

import com.meowsoft.testapp.data.mappers.toForecast
import com.meowsoft.testapp.data.remote.OpenMeteoAPI
import com.meowsoft.testapp.domain.WeatherRepository
import com.meowsoft.testapp.domain.common.Request
import com.meowsoft.testapp.domain.model.Forecast
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
