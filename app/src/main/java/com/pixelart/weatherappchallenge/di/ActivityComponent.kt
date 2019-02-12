package com.pixelart.weatherappchallenge.di

import com.pixelart.weatherappchallenge.ui.MainActivity
import dagger.Component

@Component(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: MainActivity)
}