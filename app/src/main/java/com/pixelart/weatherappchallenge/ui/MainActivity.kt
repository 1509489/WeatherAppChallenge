package com.pixelart.weatherappchallenge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pixelart.weatherappchallenge.R

class MainActivity : AppCompatActivity(), Contract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun showMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showCurrentWeather() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
