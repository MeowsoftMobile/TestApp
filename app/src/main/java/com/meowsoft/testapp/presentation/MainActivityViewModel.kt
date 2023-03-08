package com.meowsoft.testapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meowsoft.testapp.domain.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    fun getData() {
        viewModelScope
            .launch {
                runCatching {
                    weatherRepository
                        .getForecast(50.0, 23.0)
                }
            }
    }
}
