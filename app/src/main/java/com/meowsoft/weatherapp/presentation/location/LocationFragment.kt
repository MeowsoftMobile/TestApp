package com.meowsoft.weatherapp.presentation.location

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.meowsoft.weatherapp.domain.location.model.ForecastLocation
import com.meowsoft.weatherapp.presentation.location.composables.LocationFragmentContent
import com.meowsoft.weatherapp.presentation.location.state.LocationEvent
import com.meowsoft.weatherapp.presentation.location.state.LocationState
import com.meowsoft.weatherapp.presentation.ui.theme.TestAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationFragment : Fragment() {

    private val viewModel by viewModels<LocationFragmentViewModel>()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeState()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                TestAppTheme {
                    with(viewModel) {
                        val state = locationState.collectAsState()

                        val locationNameInput = locationNameInput
                        val stateValue = state.value

                        LocationFragmentContent(
                           state.value,
                            locationNameInput,
                            ::onConfirmClicked
                        )
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
    }

    private fun onConfirmClicked() {
//        viewModel.handleEvent(
//            LocationEvent.ConfirmClicked("", "")
//        )
    }

    private fun navigateToForecast(location: ForecastLocation) {
        val action = LocationFragmentDirections.actionLocationFragmentToWeatherFragment(
            argLatitude = location.latitude.toString(),
            argLongitude = location.longitude.toString()
        )
        navController.navigate(action)
    }

    private fun observeState() {
        lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel
                    .locationState
                    .onEach { state ->
                        Log.d("TestLogs", "new state: ${state::class.simpleName}")
                    }
                    .collect()
            }
        }
    }
}
