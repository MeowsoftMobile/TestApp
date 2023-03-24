package com.meowsoft.weatherapp.presentation.location.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meowsoft.weatherapp.presentation.location.state.LocationState
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun LocationFragmentContent(
    state: LocationState,
    locationInput: MutableStateFlow<String>,
    onConfirmClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val locationInputState = locationInput.collectAsState()
        val location = state.location

        // TODO move to separate composable file

        Column(
            modifier = Modifier
                .align(
                    Alignment.Center
                )
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(0.dp, 16.dp)
                    .fillMaxWidth(),
                label = {
                    Text("City")
                },
                value = locationInputState.value,
                onValueChange = {
                    locationInput.value = it
                }
            )
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else if (state.error != null) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = state.error
                )
            } else if (location != null) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "GPS location: %.4f : %.4f".format(location.latitude, location.longitude)
                )
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.2f))
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = onConfirmClick
            ) {
                Text(text = "Get Weather")
            }
        }
    }
}
