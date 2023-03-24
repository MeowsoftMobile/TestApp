package com.meowsoft.weatherapp.presentation.location

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meowsoft.weatherapp.domain.common.Result
import com.meowsoft.weatherapp.domain.location.LocationTracker
import com.meowsoft.weatherapp.domain.location.model.ForecastLocation
import com.meowsoft.weatherapp.domain.usecase.GetLocationByCityName
import com.meowsoft.weatherapp.presentation.location.state.LocationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationFragmentViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val getLocationByCityName: GetLocationByCityName
) : ViewModel() {

    val locationNameInput = MutableStateFlow("")

    private val _locationState = MutableStateFlow(LocationState())
    val locationState: StateFlow<LocationState> = _locationState

    init {
        observeLocationInput()
    }

    @OptIn(FlowPreview::class)
    private fun observeLocationInput() {
        viewModelScope
            .launch(Dispatchers.IO) {
                locationNameInput
                    .debounce(700)
                    .filter {
                        it.isNotBlank()
                    }
                    .onEach {
                        Log.d("TestLogs", "Obtaining GPS location for: $it")
                        val locationResult = resolveLocation(it)
                        handleLocationResult(locationResult)
                    }
                    .stateIn(this@launch)
            }
    }

    private fun updateLocationState(location: ForecastLocation?) {
        val lat = location?.latitude ?: 0.0
        val long = location?.longitude ?: 0.0

        val forecastLocation = ForecastLocation(
            latitude = lat,
            longitude = long
        )

        _locationState.value = _locationState.value.copy(location = forecastLocation)
    }

    private fun handleLocationResult(locationResult: Result<ForecastLocation>) =
        when (locationResult) {
            is Result.Success -> updateLocationState(locationResult.data)
            is Result.Error -> { /*TODO handle error*/
            }
        }

    private suspend fun resolveLocation(locationName: String): Result<ForecastLocation> {
        _locationState.value = _locationState.value.copy(isLoading = true)
        val location = getLocationByCityName(locationName)
        _locationState.value = _locationState.value.copy(isLoading = false)

        Log.d("TestLogs", "Location for $locationName is ${location.data?.latitude} : ${location.data?.longitude}")

        return location
    }
}
