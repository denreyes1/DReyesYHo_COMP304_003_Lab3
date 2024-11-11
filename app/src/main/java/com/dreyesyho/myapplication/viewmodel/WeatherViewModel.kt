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
    val weatherUIState = MutableStateFlow(WeathersUIState())

    init {
        getWeather("toronto")
        getWeather("calgary")
        getWeather("vancouver")
        getWeather("montreal")
        getWeather("quebec")
        getWeather("cebu")
        getWeather("hong kong")
    }

    private fun getWeather(location: String) {
        Log.i("DENSHO", "getWeathers: ")
        weatherUIState.value = WeathersUIState(isLoading = true)
        viewModelScope.launch {
            when (val result = weathersRepository.getWeather(location)) {
                is NetworkResult.Success -> {
                    Log.i("DENSHO", "weather = "+result.data.toString())
                    weatherUIState.update {
                        //TODO fix
                        val list = weatherUIState.value.weather
                        list.add(result.data)
                        it.copy(isLoading = false, weather = list)
                    }
                }
                is NetworkResult.Error -> {
                    Log.i("DENSHO", "error = "+ result.error)
                    weatherUIState.update {
                        it.copy(isLoading = false, error = result.error)
                    }
                }
            }
        }
    }
}