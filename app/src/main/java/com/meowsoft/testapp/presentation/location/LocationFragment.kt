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
import androidx.lifecycle.lifecycleScope
import com.meowsoft.testapp.presentation.location.composables.LocationFragmentContent
import com.meowsoft.testapp.presentation.location.state.LocationEvent
import com.meowsoft.testapp.presentation.location.state.LocationState
import com.meowsoft.testapp.presentation.ui.theme.TestAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LocationFragment : Fragment() {

    private val viewModel by viewModels<LocationFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                TestAppTheme {
                    val locationState = viewModel.locationState.collectAsState()
                    LocationFragmentContent(
                        locationState,
                        ::onConfirmClicked
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        viewModel.handleEvent(LocationEvent.ScreenOpened)
    }

    private fun onConfirmClicked(latitude: String, longitude: String) {
        viewModel.handleEvent(
            LocationEvent.ConfirmClicked(latitude, longitude)
        )
    }

    private fun observeState() {
        lifecycleScope
            .launchWhenCreated {
                viewModel
                    .locationState
                    .onEach { state ->
                        Log.d("TestLogs", "new state: ${state::class.simpleName}")

                        if (state is LocationState.LocationValidated) {
                        }
                    }
                    .collect()
            }
    }
}
