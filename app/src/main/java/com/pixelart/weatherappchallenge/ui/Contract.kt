package com.pixelart.weatherappchallenge.ui

import com.pixelart.weatherappchallenge.model.CurrentWeatherResponse
import com.pixelart.weatherappchallenge.model.ForecastResponse

interface Contract {
    interface View{
        fun showMessage(message: String)
        fun showError(error: String)
        fun showCurrentWeather(response: CurrentWeatherResponse)
        fun showForecast(response: ForecastResponse)
    }
    interface Presenter{
        fun getCurrentWeather(latitude: Double, longitude: Double)
        fun getForecast(latitude: Double, longitude: Double)
        fun onStop()
    }
}