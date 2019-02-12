package com.pixelart.weatherappchallenge.ui

import com.pixelart.weatherappchallenge.network.NetworkService

class Presenter(private val view: Contract.View, private val apiService: NetworkService): Contract.Presenter {

    override fun getCurrentWeather(latitude: Double, longitude: Double) {

    }

    override fun onStop() {

    }
}