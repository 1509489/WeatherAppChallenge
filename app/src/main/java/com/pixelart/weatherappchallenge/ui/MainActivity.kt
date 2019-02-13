package com.pixelart.weatherappchallenge.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.pixelart.weatherappchallenge.R
import com.pixelart.weatherappchallenge.adapter.ForecastAdapter
import com.pixelart.weatherappchallenge.common.GlideApp
import com.pixelart.weatherappchallenge.common.ICONURL
import com.pixelart.weatherappchallenge.common.Utils
import com.pixelart.weatherappchallenge.di.ActivityModule
import com.pixelart.weatherappchallenge.di.DaggerActivityComponent
import com.pixelart.weatherappchallenge.model.CurrentWeatherResponse
import com.pixelart.weatherappchallenge.model.ForecastResponse
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Contract.View {
    private  val USER_REQUEST_CODE = 99

    @Inject lateinit var presenter: Contract.Presenter
    private lateinit var forecastAdapter: ForecastAdapter
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        checkPermission()

        DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .build().inject(this)

        initWeather()

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
        tvDateTime.text = Utils.INSTANCE.timestampToDateTime(response.dt.toLong())

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

    private fun initWeather(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null){
                    lastLocation = location
                    presenter.getCurrentWeather(lastLocation.latitude, lastLocation.longitude)
                    presenter.getForecast(lastLocation.latitude, lastLocation.longitude)//51.7541245, -0.2273111
                }
            }
        }
    }

    private fun checkPermission():Boolean{
        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED ) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){

                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), USER_REQUEST_CODE)
            } else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), USER_REQUEST_CODE)
            }
            false
        } else{
            true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode)
        {
            USER_REQUEST_CODE ->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                       initWeather()
                    }
                }
                else{
                    Toast.makeText(this, "Location Permission Require", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
