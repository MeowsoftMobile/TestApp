package com.meowsoft.testapp.data.remote.dto

import com.squareup.moshi.Json

data class WeatherResponseDto(

    @field:Json(name = "hourly")
    val weatherData: WeatherDataDto
)