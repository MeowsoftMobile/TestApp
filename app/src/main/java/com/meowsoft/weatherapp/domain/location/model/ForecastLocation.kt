package com.meowsoft.weatherapp.domain.location.model

data class ForecastLocation(
    val latitude: Double,
    val longitude: Double
) {
    companion object {
        fun default() = ForecastLocation(0.0, 0.0)
    }
}
