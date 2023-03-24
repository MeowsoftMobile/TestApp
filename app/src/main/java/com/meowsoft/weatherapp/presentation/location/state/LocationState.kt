package com.meowsoft.weatherapp.presentation.location.state

import com.meowsoft.weatherapp.domain.location.model.ForecastLocation

data class LocationState(
    val isLoading: Boolean = false,
    val location: ForecastLocation? = null,
    val error: String? = null
)
