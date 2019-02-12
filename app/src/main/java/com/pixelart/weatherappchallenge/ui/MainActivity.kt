package com.pixelart.weatherappchallenge.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pixelart.weatherappchallenge.R
import com.pixelart.weatherappchallenge.adapter.ForecastAdapter
import com.pixelart.weatherappchallenge.common.GlideApp
import com.pixelart.weatherappchallenge.common.ICONURL
import com.pixelart.weatherappchallenge.di.ActivityModule
import com.pixelart.weatherappchallenge.di.DaggerActivityComponent
import com.pixelart.weatherappchallenge.model.CurrentWeatherResponse
import com.pixelart.weatherappchallenge.model.ForecastResponse
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Contract.View {

    @Inject lateinit var presenter: Contract.Presenter
    private lateinit var forecastAdapter: ForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .build().inject(this)

        presenter.getCurrentWeather(51.7541245, -0.2273111)
        presenter.getForecast(51.7541245, -0.2273111)

        forecastAdapter = ForecastAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = forecastAdapter
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    @SuppressLint("SetTextI18n")
    override fun showCurrentWeather(response: CurrentWeatherResponse) {
        tvLocationName.text = "${response.name} (${response.sys.country})"
        tvDateTime.text = response.dt.toString()

        val icon = "$ICONURL${response.weather[0].icon}.png"
        GlideApp.with(this)
            .load(icon)
            .override(100, 100)
            .into(ivIcon)

        val temp = (response.main.temp - 273.15).toInt()
        tvTemperature.text = "$temp°"

        val tempMax = (response.main.tempMax - 273.15).toInt()
        val tempMin = (response.main.tempMin - 273.15).toInt()
        tvMaxMinTemp.text = "$tempMax° / $tempMin°"

        tvClouds.text = response.weather[0].description
    }

    override fun showForecast(response: ForecastResponse) {
        forecastAdapter.submitList(response.list)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}
