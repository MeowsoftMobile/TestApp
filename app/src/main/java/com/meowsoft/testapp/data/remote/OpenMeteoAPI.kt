package com.meowsoft.testapp.data.remote

import com.meowsoft.testapp.data.remote.dto.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoAPI {
    @GET("v1/forecast?hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl")
    suspend fun getForecast(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): WeatherResponseDto
}
