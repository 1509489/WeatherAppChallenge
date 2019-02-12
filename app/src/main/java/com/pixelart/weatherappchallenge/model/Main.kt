package com.pixelart.weatherappchallenge.model

import com.google.gson.annotations.SerializedName

data class Main(
    val temp: Float,
    @SerializedName("temp_min")
    val tempMin: Float,
    @SerializedName("temp_max")
    val tempMax: Float,
    val pressure: Float,
    @SerializedName("sea_level")
    val seaLevel: Float,
    @SerializedName("grnd_level")
    val groundLevel: Float,
    val humidity: Int,
    @SerializedName("temp_kf")
    val tempKF: Float
)