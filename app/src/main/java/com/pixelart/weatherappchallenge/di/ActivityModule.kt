package com.pixelart.weatherappchallenge.di

import com.pixelart.weatherappchallenge.network.NetworkService
import com.pixelart.weatherappchallenge.ui.Contract
import com.pixelart.weatherappchallenge.ui.MainActivity
import com.pixelart.weatherappchallenge.ui.Presenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class ActivityModule(private val activity: MainActivity) {
    @Provides
    @Singleton
    fun providesPresenter(networkService: NetworkService): Contract.Presenter = Presenter(activity, networkService)
}