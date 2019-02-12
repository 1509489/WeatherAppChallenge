package com.pixelart.weatherappchallenge.ui

interface Contract {
    interface View{
        fun showMessage(message: String)
        fun showError(error: String)
        fun showCurrentWeather()
    }
    interface Presenter{
        fun getCurrentWeather(latitude: Double, longitude: Double)
        fun onStop()
    }
}