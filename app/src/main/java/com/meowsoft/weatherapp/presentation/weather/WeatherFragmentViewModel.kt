package com.meowsoft.weatherapp.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meowsoft.weatherapp.domain.common.Result
import com.meowsoft.weatherapp.domain.location.model.ForecastLocation
import com.meowsoft.weatherapp.domain.weather.WeatherRepository
import com.meowsoft.weatherapp.domain.weather.model.Forecast
import com.meowsoft.weatherapp.presentation.weather.state.WeatherFragmentEvent
import com.meowsoft.weatherapp.presentation.weather.state.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
            .launch(Dispatchers.IO) {
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

    private fun handleForecastResult(forecastResult: Result<Forecast>) {
        when (forecastResult) {
            is Result.Success -> updateForecast(forecastResult.data)
            is Result.Error -> requestError(forecastResult.message)
        }
    }

    fun handleEvent(event: WeatherFragmentEvent) {
        when (event) {
            is WeatherFragmentEvent.ArgumentsObtained -> getWeatherData(event.forecastLocation)
        }
    }
}
