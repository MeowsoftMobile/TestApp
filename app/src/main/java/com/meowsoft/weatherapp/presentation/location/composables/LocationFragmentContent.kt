package com.meowsoft.weatherapp.presentation.location.composables

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun LocationFragmentContent(
    isLoading: Boolean,
    latitude: MutableStateFlow<String>,
    longitude: MutableStateFlow<String>,
    error: String?,
    onConfirmClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val latState = latitude.collectAsState()
        val longState = longitude.collectAsState()

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
            Log.d("TestLogs", "showingPB")
        } else if (error != null) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = error
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
                inputText = latState.value,
                isEnabled = isLoading.not(),
                onTextChange = {
                    latitude.value = it
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            LabeledEditText(
                labelText = "Longitude",
                inputText = longState.value,
                isEnabled = isLoading.not(),
                onTextChange = {
                    longitude.value = it
                }
            )
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
