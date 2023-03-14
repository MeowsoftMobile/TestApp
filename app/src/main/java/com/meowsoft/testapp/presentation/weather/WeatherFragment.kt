package com.meowsoft.testapp.presentation.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.meowsoft.testapp.domain.location.model.ForecastLocation
import com.meowsoft.testapp.domain.weather.model.Forecast
import com.meowsoft.testapp.presentation.ui.theme.TestAppTheme
import com.meowsoft.testapp.presentation.weather.state.WeatherFragmentEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val viewModel by viewModels<WeatherFragmentViewModel>()

    private val location: ForecastLocation? by lazy {
        arguments?.let {
            val args = WeatherFragmentArgs.fromBundle(it)
            return@lazy ForecastLocation(
                latitude = args.argLatitude.toDouble(),
                longitude = args.argLongitude.toDouble()
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        location?.let {
            viewModel.handleEvent(WeatherFragmentEvent.ArgumentsObtained(it))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                TestAppTheme {
                    val state = viewModel.weatherState.collectAsState()

                    val weather = state.value.forecast
                    val isLoading = state.value.isLoading

                    if (weather != null) {
                        WeatherFragmentContent(
                            weather,
                            isLoading
                        )
                    }
                }
            }
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel
                    .weatherState
                    .onEach { state ->
                        Log.d("TestLogs", "new state: ${state::class.simpleName}")
                    }
                    .collect()
            }
        }
    }
}

@Composable
fun WeatherFragmentContent(
    forecast: Forecast,
    isLoading: Boolean
) {
    val weatherList = forecast
        .weatherPerDay
        .entries
        .map {
            it.value
        }.flatten()

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(weatherList.size) { index ->
                Text(
                    text = "${weatherList[index].temperature} °C"
                )
            }
        }
    }
}
