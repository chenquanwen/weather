package com.example.a0720.logic.Model

import java.util.*

data class HourlyResponse(val status: String, val result: Result){
    data class Result(val hourly: Hourly)
    data class Hourly(val temperature: List<Temperature>, val skycon: List<Skycon>)
    data class Temperature(val datetime : Date, val value: String)
    data class Skycon(val value: String, val datetime : Date)
}

