package com.vanilaque.mangaque.service

import com.vanilaque.mangaque.presentation.components.FooterPath
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object StateManager {
    private val _showBottomTopbars = MutableStateFlow(false)
    val showBottomTopbars: StateFlow<Boolean> = _showBottomTopbars.asStateFlow()

    private val _footerPath = MutableStateFlow(FooterPath.CATALOG)
    val footerPath: StateFlow<FooterPath> = _footerPath.asStateFlow()

    fun setShowBottomTopBars(value: Boolean) {
        _showBottomTopbars.value = value
    }

    fun setFooterPath(value: FooterPath) {
        _footerPath.value = value
    }
}