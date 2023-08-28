package com.vanilaque.mangaque.service

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object StateManager {
    private val _showBottomTopbars = MutableStateFlow(false)
    val showBottomTopbars: StateFlow<Boolean> = _showBottomTopbars.asStateFlow()

    fun setShowBottomTopBars(value: Boolean) {
        _showBottomTopbars.value = value
    }
}