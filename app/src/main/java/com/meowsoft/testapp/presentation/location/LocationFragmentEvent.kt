package com.meowsoft.testapp.presentation.location

import com.meowsoft.testapp.domain.location.model.ForecastLocation

sealed class LocationFragmentEvent {
    class LocationConfirmedEvent(
        location: ForecastLocation
    ) : LocationFragmentEvent()
}
