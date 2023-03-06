package com.meowsoft.testapp.data

import com.meowsoft.testapp.domain.WeatherRepository
import javax.inject.Inject

class OpenMeteoWeatherRepository @Inject constructor() : WeatherRepository {
    override fun getForecast() {
        TODO("Not yet implemented")
    }
}
