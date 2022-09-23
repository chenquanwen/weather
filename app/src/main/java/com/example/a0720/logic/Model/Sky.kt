package com.example.a0720.logic.Model

import com.example.a0720.R

class Sky(val info: String, val icon: Int)

private val sky = mapOf(
    "CLEAR_DAY" to Sky("晴", R.drawable.ic__00),
    "CLEAR_NIGHT" to Sky("晴", R.drawable.ic__00),
    "PARTLY_CLOUDY_DAY" to Sky("多云", R.drawable.ic__01),
    "PARTLY_CLOUDY_NIGHT" to Sky("多云", R.drawable.ic__01),
    "CLOUDY" to Sky("阴", R.drawable.ic__04),
    "WIND" to Sky("大风", R.drawable.ic__04),
    "LIGHT_RAIN" to Sky("小雨", R.drawable.ic__05_fill),
    "MODERATE_RAIN" to Sky("中雨", R.drawable.ic__06),
    "HEAVY_RAIN" to Sky("大雨", R.drawable.ic__07),
    "STORM_RAIN" to Sky("暴雨", R.drawable.ic__10),
    "THUNDER_SHOWER" to Sky("雷阵雨", R.drawable.ic__02),
    "SLEET" to Sky("雨夹雪", R.drawable.ic__404),
    "LIGHT_SNOW" to Sky("小雪", R.drawable.ic__400),
    "MODERATE_SNOW" to Sky("中雪", R.drawable.ic__401),
    "HEAVY_SNOW" to Sky("大雪", R.drawable.ic__402),
    "STORM_SNOW" to Sky("暴雪", R.drawable.ic__403),
    "HAIL" to Sky("冰雹", R.drawable.ic__304),
    "LIGHT_HAZE" to Sky("轻度雾霾", R.drawable.ic__511),
    "MODERATE_HAZE" to Sky("中度雾霾", R.drawable.ic__512),
    "HEAVY_HAZE" to Sky("重度雾霾", R.drawable.ic__51),
    "FOG" to Sky("雾", R.drawable.ic__509),
    "DUST" to Sky("浮尘", R.drawable.ic__503)
)

fun getSky(skycon: String): Sky {
    return sky[skycon] ?: sky["CLEAR_DAY"]!!
}
