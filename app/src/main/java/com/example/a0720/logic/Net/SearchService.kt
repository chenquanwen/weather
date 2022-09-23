package com.example.a0720.Net

import com.example.a0720.logic.Model.PlaceResponse
import com.example.a0720.MyApplication
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("v2/place?token=${MyApplication.token}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}