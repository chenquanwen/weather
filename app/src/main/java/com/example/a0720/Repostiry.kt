package com.example.a0720

import androidx.lifecycle.liveData
import com.example.a0720.Net.NetWork
import com.example.a0720.logic.Dao.PlaceDao
import com.example.a0720.logic.Model.Place
import com.example.a0720.logic.Model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repostiry {


    fun searchPlace(query: String) = fire(Dispatchers.IO){
        val placeResponse = NetWork.searchPlace(query)
        if(placeResponse.status == "ok"){
            val place = placeResponse.places
            Result.success(place)
        }else{
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refrashWeather(lng: String, lat: String) = fire(Dispatchers.IO){
        coroutineScope {
            val deferredDailyResponse = async {
                NetWork.getDailyWeather(lng, lat)
            }
            val deferredRealtimeResponse = async {
                NetWork.getRealtimeWeather(lng, lat)
            }
            val deferredHourlyResponse = async {
                NetWork.getHourlyWeather(lng, lat)
            }
            val DailyResponse = deferredDailyResponse.await()
            val RealtimeResponse = deferredRealtimeResponse.await()
            val HourlyResponse = deferredHourlyResponse.await()

            if(DailyResponse.status == "ok" && RealtimeResponse.status == "ok" && HourlyResponse.status == "ok"){
                val weather = Weather(DailyResponse.result.daily, RealtimeResponse.result.realtime, HourlyResponse.result.hourly)
                Result.success(weather)
            }else{
                Result.failure(RuntimeException("daily status is ${DailyResponse.status}" +
                        "realtime status is ${RealtimeResponse.status}" +
                        "hourly status is ${HourlyResponse.status}"))
            }
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }



    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}