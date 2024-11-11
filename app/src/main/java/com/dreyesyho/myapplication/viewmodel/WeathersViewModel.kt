package com.dreyesyho.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreyesyho.myapplication.data.NetworkResult
import com.dreyesyho.myapplication.data.WeathersRepository
import com.dreyesyho.myapplication.views.WeathersUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeathersViewModel(
    private val weathersRepository: WeathersRepository
): ViewModel() {
    val weathersUIState = MutableStateFlow(WeathersUIState())

    init {
        getPets()
    }

    private fun getPets() {
        weathersUIState.value = WeathersUIState(isLoading = true)
        viewModelScope.launch {
            when (val result = weathersRepository.getPets()) {
                is NetworkResult.Success -> {
                    weathersUIState.update {
                        it.copy(isLoading = false, pets = result.data)
                    }
                }
                is NetworkResult.Error -> {
                    weathersUIState.update {
                        it.copy(isLoading = false, error = result.error)
                    }
                }
            }
        }
    }
}