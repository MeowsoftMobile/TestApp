package com.meowsoft.weatherapp.domain.usecase

import com.meowsoft.weatherapp.domain.location.LocationTracker
import javax.inject.Inject

class GetLocationByCityName @Inject constructor(
    private val locationTracker: LocationTracker
) {
    suspend operator fun invoke(locationName: String) = locationTracker
        .getLocationByCityName(locationName)
}
