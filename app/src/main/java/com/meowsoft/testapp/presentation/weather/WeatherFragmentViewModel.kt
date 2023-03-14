package com.meowsoft.testapp.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meowsoft.testapp.domain.common.Request
import com.meowsoft.testapp.domain.location.model.ForecastLocation
import com.meowsoft.testapp.domain.weather.WeatherRepository
import com.meowsoft.testapp.domain.weather.model.Forecast
import com.meowsoft.testapp.presentation.weather.state.WeatherFragmentEvent
import com.meowsoft.testapp.presentation.weather.state.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherFragmentViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weatherState: MutableStateFlow<WeatherState> = MutableStateFlow(WeatherState.Default)
    val weatherState: StateFlow<WeatherState> = _weatherState

    private fun getWeatherData(forecastLocation: ForecastLocation) {
        viewModelScope
            .launch {
                _weatherState.value = WeatherState.LoadingForecast
                val result = weatherRepository
                    .getForecast(
                        forecastLocation.latitude,
                        forecastLocation.longitude
                    )

                handleForecastResult(result)
            }
    }

    private fun updateForecast(forecast: Forecast?) {
        forecast?.let {
            _weatherState.value = WeatherState.ForecastObtained(it)
        }
    }

    private fun requestError(message: String?) {
        message?.let {
            _weatherState.value = WeatherState.Error(it)
        }
    }

    private fun handleForecastResult(forecastResult: Request<Forecast>) {
        when (forecastResult) {
            is Request.Success -> updateForecast(forecastResult.data)
            is Request.Error -> requestError(forecastResult.message)
        }
    }

    fun handleEvent(event: WeatherFragmentEvent) {
        when (event) {
            is WeatherFragmentEvent.ArgumentsObtained -> getWeatherData(event.forecastLocation)
        }
    }
}
