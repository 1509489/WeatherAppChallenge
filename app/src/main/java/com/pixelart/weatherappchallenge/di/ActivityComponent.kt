package com.pixelart.weatherappchallenge.di

import com.pixelart.weatherappchallenge.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: MainActivity)
}