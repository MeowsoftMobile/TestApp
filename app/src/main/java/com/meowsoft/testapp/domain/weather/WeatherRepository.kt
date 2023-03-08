package com.meowsoft.testapp.domain.weather

import com.meowsoft.testapp.domain.common.Request
import com.meowsoft.testapp.domain.weather.model.Forecast

interface WeatherRepository {
    suspend fun getForecast(lat: Double, long: Double): Request<Forecast>
}
