package com.meowsoft.weatherapp.domain.weather

import com.meowsoft.weatherapp.domain.common.Result
import com.meowsoft.weatherapp.domain.weather.model.Forecast

interface WeatherRepository {
    suspend fun getForecast(lat: Double, long: Double): Result<Forecast>
}
