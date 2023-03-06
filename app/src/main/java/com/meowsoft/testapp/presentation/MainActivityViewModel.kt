package com.meowsoft.testapp.presentation

import androidx.lifecycle.ViewModel
import com.meowsoft.testapp.domain.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel()
