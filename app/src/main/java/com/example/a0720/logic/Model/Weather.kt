package com.example.a0720.logic.Model

    data class Weather(val daily: DailyResponse.Daily, val realtime: RealtimeResponse.Realtime, val hourly: HourlyResponse.Hourly)
