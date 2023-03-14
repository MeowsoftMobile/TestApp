package com.meowsoft.testapp.presentation.location

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
import com.meowsoft.testapp.presentation.location.composables.LocationFragmentContent
import com.meowsoft.testapp.presentation.location.state.LocationEvent
import com.meowsoft.testapp.presentation.location.state.LocationState
import com.meowsoft.testapp.presentation.ui.theme.TestAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationFragment : Fragment() {

    private val viewModel by viewModels<LocationFragmentViewModel>()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                TestAppTheme {
                    val locationState = viewModel
                        .locationState
                        .collectAsState()

                    val latInput = viewModel.latInput
                    val longInput = viewModel.longInput

                    val stateValue = locationState.value

                    LocationFragmentContent(
                        stateValue.isLoading,
                        latInput,
                        longInput,
                        stateValue.error,
                        ::onConfirmClicked
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        observeState()
        viewModel.handleEvent(LocationEvent.ScreenOpened)
    }

    private fun onConfirmClicked() {
        viewModel.handleEvent(
            LocationEvent.ConfirmClicked("", "")
        )
    }

    private fun navigateToForecast() {
        val action = LocationFragmentDirections.actionLocationFragmentToWeatherFragment(
            argLatitude = viewModel.latInput.value.toFloat(),
            argLongitude = viewModel.longInput.value.toFloat()
        )
        navController.navigate(action)
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel
                    .locationState
                    .onEach { state ->
                        Log.d("TestLogs", "new state: ${state::class.simpleName}")
                        if (state is LocationState.LocationValidated) {
                            navigateToForecast()
                        }
                    }
                    .collect()
            }
        }
    }
}
