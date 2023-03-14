package com.meowsoft.weatherapp.presentation.location

import com.meowsoft.weatherapp.domain.location.model.ForecastLocation

sealed class LocationFragmentEvent {
    class LocationConfirmedEvent(
        location: ForecastLocation
    ) : LocationFragmentEvent()
}
