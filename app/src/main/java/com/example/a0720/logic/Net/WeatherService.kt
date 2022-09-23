package com.example.a0720.Net

import com.example.a0720.logic.Model.DailyResponse
import com.example.a0720.logic.Model.HourlyResponse
import com.example.a0720.logic.Model.RealtimeResponse
import com.example.a0720.MyApplication
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    @GET("v2.5/${MyApplication.token}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<RealtimeResponse>

    @GET("v2.5/${MyApplication.token}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DailyResponse>

    @GET("v2.6/${MyApplication.token}/{lng},{lat}/hourly.json")
    fun getHourlyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<HourlyResponse>

}