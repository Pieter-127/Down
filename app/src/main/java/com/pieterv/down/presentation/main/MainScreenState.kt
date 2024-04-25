package com.pieterv.down.presentation.main

import com.pieterv.down.domain.usecase.ComplexData

data class MainScreenState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val data: List<ComplexData> = emptyList()
)