package com.pixelart.weatherappchallenge

import com.pixelart.weatherappchallenge.model.*
import com.pixelart.weatherappchallenge.network.NetworkService
import com.pixelart.weatherappchallenge.ui.Contract
import com.pixelart.weatherappchallenge.ui.Presenter
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Test

import org.junit.Before
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executor

@RunWith(MockitoJUnitRunner::class)
class PresenterTest {

    private lateinit var presenter: Presenter
    private lateinit var currentWeatherResponse: CurrentWeatherResponse
    private lateinit var forecastResponse: ForecastResponse
    private lateinit var coordinates: Pair<Double, Double>

    @Mock private lateinit var view: Contract.View
    @Mock private lateinit var networkService: NetworkService

    companion object {
        @BeforeClass
        @JvmStatic
        fun setupSchedulers(){
            val scheduler = object : Scheduler() {
                override fun createWorker(): Scheduler.Worker {
                    return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
                }
            }

            RxJavaPlugins.setInitIoSchedulerHandler { scheduler }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler }
        }
    }

    @Before
    fun setup(){
        presenter = Presenter(view, networkService)
        coordinates = Pair(53.75, -0.24)

        val weather = ArrayList<Weather>()
        val main = Main(0F,0.0F,0.0F,0.0F,0.0F,0.0F,1,0.0F)
        val wind = Wind(0.0, 0.0)
        val sys = Sys("",1,1,0.0F,"", 1, 1)
        val weatherList = ArrayList<WeatherList>()
        val city = City(1, "", Coord(coordinates.first, coordinates.second), "")

        currentWeatherResponse = CurrentWeatherResponse(
            Coord(coordinates.first, coordinates.second), weather, "Test Base",main,1,
            wind,Clouds(1),1, sys,1,"",1)

        forecastResponse = ForecastResponse("", 0F, 1, weatherList, city)
    }

    @Test
    fun testCurrentWeatherDataFetch() {
        Mockito.`when`(networkService.getCurrentWeather(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyString()))
            .thenReturn(Single.just(currentWeatherResponse))

        presenter.getCurrentWeather(coordinates.first, coordinates.second)
        Mockito.verify(view).showCurrentWeather(currentWeatherResponse)
        Assert.assertEquals("Test Base", currentWeatherResponse.base)
        Assert.assertEquals(1, currentWeatherResponse.sys.id)
    }

    @Test
    fun testForecastDataFetch(){
        Mockito.`when`(networkService.getForecast(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyString()))
            .thenReturn(Single.just(forecastResponse))

        presenter.getForecast(coordinates.first, coordinates.second)
        Mockito.verify(view).showForecast(forecastResponse)
        Assert.assertEquals(53.75, forecastResponse.city.coord.lat, 0.0)
    }
}
