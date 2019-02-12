package com.pixelart.weatherappchallenge.model

data class ForecastResponse(
    val cod: String,
    val message: Float,
    val cnt: Int,
    val list: List<WeatherList>,
    val city: City
)