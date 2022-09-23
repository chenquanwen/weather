package com.example.a0720.logic.Dao

import android.content.Context
import androidx.core.content.edit
import com.example.a0720.MyApplication
import com.example.a0720.logic.Model.Place
import com.google.gson.Gson

object PlaceDao {


    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }

    }

    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = MyApplication.context.getSharedPreferences("0720", Context.MODE_PRIVATE)

}