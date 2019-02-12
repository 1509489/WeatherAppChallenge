package com.pixelart.weatherappchallenge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pixelart.weatherappchallenge.R
import com.pixelart.weatherappchallenge.common.GlideApp
import com.pixelart.weatherappchallenge.common.ICONURL
import com.pixelart.weatherappchallenge.model.WeatherList
import kotlinx.android.synthetic.main.recyclerview_layout.view.*

class ForecastAdapter : ListAdapter<WeatherList, ForecastAdapter.ViewHolder>(DIFF_CALLBACK){
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastAdapter.ViewHolder {
        context = parent.context
        val rootView = LayoutInflater.from(context).inflate(R.layout.recyclerview_layout, parent, false)
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ForecastAdapter.ViewHolder, position: Int) {
        val weatherList = getItem(position)
        holder.initViews(weatherList, context)
    }

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        private val tvDateTime: TextView = view.tvDateTime
        private val tvWeatherDescription: TextView = view.tvWeatherDesc
        private val tvTemperature: TextView = view.tvTemp

        fun initViews(weatherList: WeatherList, context: Context){
            tvDateTime.text = weatherList.dt.toString()

            val temp = (weatherList.main.temp - 273.15).toInt()
            tvTemperature.text = "$temp"
            var icon = ""
            for (weather in weatherList.weather) {
                tvWeatherDescription.text = weather.description
                icon = "$ICONURL${weather.icon}.png"
            }

            GlideApp.with(context)
                .load(icon)
                .into(view.ivWeatherIcon)
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<WeatherList> = object : DiffUtil.ItemCallback<WeatherList>() {
            override fun areItemsTheSame(oldItem: WeatherList, newItem: WeatherList): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: WeatherList, newItem: WeatherList): Boolean {
                return oldItem == newItem
            }

        }
    }
}