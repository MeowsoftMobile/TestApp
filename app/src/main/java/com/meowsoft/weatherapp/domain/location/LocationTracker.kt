package com.meowsoft.weatherapp.domain.location

import android.location.Location
import com.meowsoft.weatherapp.domain.common.Result
import com.meowsoft.weatherapp.domain.location.model.ForecastLocation

interface LocationTracker {
    suspend fun getLocation(): Location?
    suspend fun getLocationByCityName(locationName: String): Result<ForecastLocation>
}
