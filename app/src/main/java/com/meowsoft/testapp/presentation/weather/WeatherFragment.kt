package com.meowsoft.testapp.presentation.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.meowsoft.testapp.domain.location.model.ForecastLocation
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

                    WeatherFragmentContent()
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

@Preview
@Composable
fun WeatherFragmentContent() {
}
