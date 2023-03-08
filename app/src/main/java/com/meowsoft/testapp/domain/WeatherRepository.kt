package com.meowsoft.testapp.domain

import com.meowsoft.testapp.domain.common.Request
import com.meowsoft.testapp.domain.model.Forecast

interface WeatherRepository {
    suspend fun getForecast(lat: Double, long: Double): Request<Forecast>
}
