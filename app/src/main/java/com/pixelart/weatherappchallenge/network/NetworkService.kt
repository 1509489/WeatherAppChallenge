package com.pixelart.weatherappchallenge.network

import com.pixelart.weatherappchallenge.model.CurrentWeatherResponse
import com.pixelart.weatherappchallenge.model.ForecastResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("data/2.5/weather")
    fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") appId: String
    ):Single<CurrentWeatherResponse>

    @GET("data/2.5/forecast")
    fun getForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") appId: String
    ):Single<ForecastResponse>
}