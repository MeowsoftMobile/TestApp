package com.meowsoft.testapp.domain.location

import android.location.Location

interface LocationTracker {
    suspend fun getLocation(): Location?
}
