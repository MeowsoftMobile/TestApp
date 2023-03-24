package com.meowsoft.weatherapp.data.common // ktlint-disable filename

import android.location.Address
import com.meowsoft.weatherapp.domain.location.model.ForecastLocation

fun Address.toForecastLocation() = ForecastLocation(
    latitude = latitude,
    longitude = longitude
)
