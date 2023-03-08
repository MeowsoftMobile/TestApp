package com.meowsoft.testapp.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meowsoft.testapp.domain.location.LocationTracker
import com.meowsoft.testapp.domain.weather.WeatherRepository
import com.meowsoft.testapp.domain.weather.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.meowsoft.testapp.domain.common.Request

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun getForecast() {
        viewModelScope
            .launch {
                state = state.copy(isLoading = true)
                locationTracker.getLocation()?.let { location ->
                    val result = weatherRepository.getForecast(
                        location.latitude,
                        location.longitude
                    )
                    state = when(result) {
                        is Request.Success -> {
                            state.copy(isLoading = false, forecast = result.data, error = null)
                        }
                        is Request.Error -> {
                            state.copy(isLoading = false, forecast = null, error = result.message)
                        }
                    }
                } ?: kotlin.run {
                    state = state.copy(isLoading = false, forecast = null, error = "Couldn't get location")
                }
            }
    }
}
