package com.meowsoft.testapp.presentation.location.state

sealed class LocationEvent {
    object ScreenOpened : LocationEvent()
    data class ConfirmClicked(
        val latitude: String,
        val longitude: String
    ) : LocationEvent()
}
