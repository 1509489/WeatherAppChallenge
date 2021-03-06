package com.pixelart.weatherappchallenge.ui

import com.pixelart.weatherappchallenge.common.APP_ID
import com.pixelart.weatherappchallenge.network.NetworkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class Presenter(private val view: Contract.View, private val networkService: NetworkService): Contract.Presenter {
    private val compositeDisposable = CompositeDisposable()

    override fun getCurrentWeather(latitude: Double, longitude: Double) {
        compositeDisposable.add(
            networkService.getCurrentWeather(latitude, longitude, APP_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showCurrentWeather, this::handleError)
        )
    }

    override fun getForecast(latitude: Double, longitude: Double) {
        compositeDisposable.add(
            networkService.getForecast(latitude, longitude, APP_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showForecast, this::handleError)
        )
    }

    override fun onStop() {
        compositeDisposable.clear()
    }

    private fun handleError(throwable: Throwable){
        if (throwable.message != null){
            view.showError("Error: Unable to get weather info, please try again")
            throwable.printStackTrace()
        }
    }
}