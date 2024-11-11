package com.dreyesyho.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreyesyho.myapplication.data.NetworkResult
import com.dreyesyho.myapplication.data.WeathersRepository
import com.dreyesyho.myapplication.views.WeathersUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weathersRepository: WeathersRepository
): ViewModel() {
    val weathersUIState = MutableStateFlow(WeathersUIState())

    init {
        getWeathers()
    }

    private fun getWeathers() {
        Log.i("DENSHO", "getWeathers: ")
        weathersUIState.value = WeathersUIState(isLoading = true)
        viewModelScope.launch {
            when (val result = weathersRepository.getWeather()) {
                is NetworkResult.Success -> {
                    Log.i("DENSHO", "weather = "+weathersRepository.getWeather().toString())
                    weathersUIState.update {
                        //TODO fix
                        val list = weathersUIState.value.weather
                        list.add(result.data)
                        it.copy(isLoading = false, weather = list)
                    }
                }
                is NetworkResult.Error -> {
                    Log.i("DENSHO", "error = "+ result.error)
                    weathersUIState.update {
                        it.copy(isLoading = false, error = result.error)
                    }
                }
            }
        }
    }
}