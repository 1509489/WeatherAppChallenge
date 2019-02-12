package com.pixelart.weatherappchallenge.model

data class WeatherList(
    val dt: Int,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val rain: Rain,
    val sys: Sys,
    val dt_txt: String
)