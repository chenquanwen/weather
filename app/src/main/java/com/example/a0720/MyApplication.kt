package com.example.a0720

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class MyApplication: Application() {

    companion object{
        const val token = "DSK0z2epAjuyTZiN"
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}