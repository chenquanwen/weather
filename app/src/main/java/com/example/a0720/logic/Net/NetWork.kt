package com.example.a0720.Net

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object NetWork {

    //创建retrofit动态代理对象，并调用接口的对象实现发起搜索城市请求
    private val PlaceService = ServiceCreator.create<SearchService>()

    suspend fun searchPlace(query: String) = PlaceService.searchPlaces(query).await()

    //创建retrofit动态代理对象，并调用接口的对象实现发起获取城市天气请求
    private val WeatherService = ServiceCreator.create<WeatherService>()

    suspend fun getDailyWeather(lng: String, lat: String) = WeatherService.getDailyWeather(lng, lat).await()

    suspend fun getRealtimeWeather(lng: String, lat: String) = WeatherService.getRealtimeWeather(lng, lat).await()

    suspend fun getHourlyWeather(lng: String, lat: String) = WeatherService.getHourlyWeather(lng, lat).await()

    //重写await方法
    private suspend fun <T> Call<T>.await(): T{
        return suspendCoroutine {
            enqueue(object : Callback<T>{
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if(body != null) it.resume(body) else it.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    it.resumeWithException(t)
                }

            })
        }
    }

}