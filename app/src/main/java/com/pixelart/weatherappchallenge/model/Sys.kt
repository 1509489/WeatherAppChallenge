package com.pixelart.weatherappchallenge.model

data class Sys(
    val pod: String,
    val type: Int,
    val id: Int,
    val message: Float,
    val country: String,
    val sunrise: Int,
    val sunset: Int
)