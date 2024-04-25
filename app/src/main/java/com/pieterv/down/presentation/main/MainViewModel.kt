package com.pieterv.down.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pieterv.down.domain.usecase.FetchInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val validateLoginUseCaseImpl: FetchInfoUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenState())
    val uiState: StateFlow<MainScreenState> = _uiState

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            MainScreenEvent.LoadData -> {
                viewModelScope.launch {
                    try {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                hasError = false
                            )
                        }
                        val result = validateLoginUseCaseImpl()
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                hasError = false,
                                data = result
                            )
                        }
                    } catch (ex: Exception) {
                        _uiState.update {
                            it.copy(
                                hasError = true,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }
}