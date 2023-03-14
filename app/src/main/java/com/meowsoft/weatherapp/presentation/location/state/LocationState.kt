package com.meowsoft.weatherapp.presentation.location.state

import com.meowsoft.weatherapp.domain.location.model.ForecastLocation

sealed class LocationState(
    val isLoading: Boolean = false,
    val location: ForecastLocation? = null,
    val error: String? = null
) {
    object Default : LocationState(isLoading = false, location = null, error = null)
    object LoadingLocation : LocationState(isLoading = true, location = null, error = null)
    class LocationObtained(location: ForecastLocation) : LocationState(isLoading = false, location = location, error = null)
    class Error(message: String) : LocationState(isLoading = false, location = null, error = message)
    class LocationValidated(forecastLocation: ForecastLocation) : LocationState(isLoading = false, location = forecastLocation, error = null)
}
