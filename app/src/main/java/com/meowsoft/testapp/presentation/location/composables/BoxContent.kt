package com.meowsoft.testapp.presentation.location.composables

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meowsoft.testapp.presentation.location.state.LocationState

@Composable
fun LocationFragmentContent(
    state: State<LocationState>,
    onConfirmClick: (lat: String, long: String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val stateValue = state.value

        val latInput = remember { mutableStateOf("") }
        val longInput = remember { mutableStateOf("") }

        if (stateValue.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
            Log.d("TestLogs", "showingPB")
        } else if (stateValue is LocationState.Error) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = stateValue.error ?: "No message"
            )
            Log.d("TestLogs", "NOT showingPB")
        }
        Column(
            modifier = Modifier.align(
                Alignment.BottomCenter
            )
        ) {
            LabeledEditText(
                labelText = "Latitude",
                inputText = latInput.value,
                onTextChange = {
                    latInput.value = it
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            LabeledEditText(
                labelText = "Longitude",
                inputText = longInput.value,
                onTextChange = {
                    longInput.value = it
                }
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.2f))
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    onConfirmClick(
                        latInput.value,
                        longInput.value
                    )
                }
            ) {
                Text(text = "Get Weather")
            }
        }
    }
}
