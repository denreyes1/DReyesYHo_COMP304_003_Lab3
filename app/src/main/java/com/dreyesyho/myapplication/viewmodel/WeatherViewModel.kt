package com.dreyesyho.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreyesyho.myapplication.data.NetworkResult
import com.dreyesyho.myapplication.data.WeatherResponse
import com.dreyesyho.myapplication.data.WeathersRepository
import com.dreyesyho.myapplication.data.asResult
import com.dreyesyho.myapplication.views.WeathersUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weathersRepository: WeathersRepository
): ViewModel() {
    val weatherUIState = MutableStateFlow(WeathersUIState())
    private val _favoriteWeathers = MutableStateFlow<List<WeatherResponse>>(emptyList())
    val favoriteWeathers: StateFlow<List<WeatherResponse>> get() = _favoriteWeathers

    init {
        getWeather("toronto")
        getWeather("calgary")
        getWeather("vancouver")
        getWeather("montreal")
        getWeather("quebec")
        getWeather("cebu")
        getWeather("hong kong")
        getWeather("mexico city")
    }

    private fun getWeather(location: String) {
        weatherUIState.value = WeathersUIState(isLoading = true)
        viewModelScope.launch {
            weathersRepository.getWeathers().asResult().collect() { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        weatherUIState.update {
                            it.copy(isLoading = false, weather = result.data)
                        }
                    }
                    is NetworkResult.Error -> {
                        weatherUIState.update {
                            it.copy(isLoading = false, error = result.error)
                        }
                    }
                }
            }
        }
    }

    fun updateWeather(weather: WeatherResponse) {
        if (!viewModelScope.isActive) {
            return
        }
        viewModelScope.launch {
            weathersRepository.updateWeather(weather)
        }
    }

    fun getFavoriteWeathers() {
        viewModelScope.launch {
            weathersRepository.getFavoriteWeather().collect {
                _favoriteWeathers.value = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}