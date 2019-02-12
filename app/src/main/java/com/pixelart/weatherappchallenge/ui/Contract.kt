package com.pixelart.weatherappchallenge.ui

import com.pixelart.weatherappchallenge.model.CurrentWeatherResponse

interface Contract {
    interface View{
        fun showMessage(message: String)
        fun showError(error: String)
        fun showCurrentWeather(currentWeatherResponse: CurrentWeatherResponse)
    }
    interface Presenter{
        fun getCurrentWeather(latitude: Double, longitude: Double)
        fun onStop()
    }
}