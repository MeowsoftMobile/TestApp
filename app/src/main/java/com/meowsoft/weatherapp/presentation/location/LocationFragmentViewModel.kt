package com.meowsoft.weatherapp.presentation.location

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meowsoft.weatherapp.domain.location.LocationTracker
import com.meowsoft.weatherapp.domain.location.model.ForecastLocation
import com.meowsoft.weatherapp.presentation.location.state.LocationEvent
import com.meowsoft.weatherapp.presentation.location.state.LocationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationFragmentViewModel @Inject constructor(
    private val locationTracker: LocationTracker
) : ViewModel() {

    val latInput = MutableStateFlow("")
    val longInput = MutableStateFlow("")

    private val _locationState = MutableStateFlow<LocationState>(LocationState.Default)
    val locationState: StateFlow<LocationState> = _locationState

    init {
        viewModelScope
            .launch {
                _locationState
                    .onEach {
                        if (it is LocationState.LocationObtained) {
                            latInput.value = it.location?.latitude?.toString() ?: ""
                            longInput.value = it.location?.longitude?.toString() ?: ""
                        }
                    }
                    .stateIn(this)
            }
    }

    fun handleEvent(event: LocationEvent) {
        when (event) {
            is LocationEvent.ScreenOpened -> getLocation()
            is LocationEvent.ConfirmClicked -> validateLocation()
        }
    }

    private fun validateLocation() {
        // do some validation
        val lat = latInput.value.toDouble()
        val long = longInput.value.toDouble()

        val location = ForecastLocation(
            latitude = lat,
            longitude = long
        )

        _locationState.value = LocationState.LocationValidated(location)
    }

    private fun getLocation() {
        viewModelScope
            .launch {
                Log.d("TestLogs", "Obtaining Location")
                _locationState.value = LocationState.LoadingLocation
                locationTracker
                    .getLocation()?.let { location ->
                        val forecastLocation = ForecastLocation(
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                        Log.d("TestLogs", "Location obtained - updating state")
                        _locationState.value = LocationState.LocationObtained(forecastLocation)
                    } ?: kotlin.run { _locationState.value = LocationState.Error("Couldn't obtain the location") }
            }
    }
}
