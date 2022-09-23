package com.example.a0720.UI.Weather

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.a0720.R
import com.example.a0720.logic.Model.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WeatherActivity : AppCompatActivity() {
    val drawerLayout: DrawerLayout by lazy { findViewById(R.id.drawer) }
    val viewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }
    val dailyList = ArrayList<Daily>()
    val hourlyList = ArrayList<Hourly>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        window.statusBarColor = Color.TRANSPARENT

        val toolbar : Toolbar = findViewById(R.id.toolbar)
        val swipe: SwipeRefreshLayout = findViewById(R.id.swipeRefresh)
        val background : ImageView = findViewById(R.id.bg)

        //设置背景
        Glide.with(this).load(R.drawable.a).into(background)

        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }
        viewModel.weatherLiveData.observe(this, androidx.lifecycle.Observer {
            val weather = it.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                it.exceptionOrNull()?.printStackTrace()
            }
            swipe.isRefreshing = false
        })

        //刷新
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
        swipe.setColorSchemeResources(R.color.white)
        swipe.setOnRefreshListener {
            viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
            swipe.isRefreshing = true
        }


        //hideInput
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener{
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

            override fun onDrawerOpened(drawerView: View) {}

            override fun onDrawerClosed(drawerView: View) {
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(drawerView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }

            override fun onDrawerStateChanged(newState: Int) {}

        })

    }

    private fun showWeatherInfo(weather: Weather) {

        val placeName : TextView = findViewById(R.id.placeName)
        val currentTemp: TextView = findViewById(R.id.currentTemp)
        val currentSky: TextView = findViewById(R.id.currentSky)
        val currentAQI: TextView = findViewById(R.id.currentAQI)
        val coldRiskText: TextView = findViewById(R.id.coldRiskText)
        val dressingText: TextView = findViewById(R.id.dressingText)
        val ultravioletText : TextView = findViewById(R.id.ultravioletText)
        val carWashingText : TextView = findViewById(R.id.carWashingText)
        val DailyRecy: RecyclerView = findViewById(R.id.DailyRecy)
        val HourlyRecy: RecyclerView = findViewById(R.id.hourlyRecy)

        placeName.text = viewModel.placeName
        val realtime = weather.realtime
        val daily = weather.daily
        val hourly = weather.hourly


        // now.xml
        val currentTempText = "${realtime.temperature.toInt()} ℃"
        currentTemp.text = currentTempText
        currentSky.text = getSky(realtime.skycon).info
        val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        currentAQI.text = currentPM25Text

        // forecast_daily.xml
        initDailyList(daily)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        DailyRecy.layoutManager = layoutManager
        val adapter = ForecastDailyAdapter(this, dailyList)
        DailyRecy.adapter = adapter


        // forecast_hourly.xml
        initHourlyList(hourly)
        val layoutManager_hourly = LinearLayoutManager(this)
        layoutManager_hourly.orientation = LinearLayoutManager.HORIZONTAL
        HourlyRecy.layoutManager = layoutManager_hourly
        val adapter_hourly = ForecastHourlyAdapter(this, hourlyList)
        HourlyRecy.adapter = adapter_hourly

        //life_index.xml
        val lifeIndex = daily.lifeIndex
        coldRiskText.text = lifeIndex.coldRisk[0].desc
        dressingText.text = lifeIndex.dressing[0].desc
        ultravioletText.text = lifeIndex.ultraviolet[0].desc
        carWashingText.text = lifeIndex.carWashing[0].desc

    }

    private fun initHourlyList(hourly: HourlyResponse.Hourly) {
        hourlyList.clear()
        for (i in 0 until 12) {
            val skycon = hourly.skycon[i]
            val temperature = hourly.temperature[i]
            val simpleDateFormat = SimpleDateFormat("H时", Locale.getDefault())
            val time = simpleDateFormat.format(temperature.datetime)
            val sky = getSky(skycon.value)
            val Icon = sky.icon
            val tempText = "${temperature.value} ℃"
            val temperatureInfo = tempText
            hourlyList.add(Hourly(temperatureInfo,Icon,time))
        }
    }


    fun initDailyList(daily: DailyResponse.Daily){
        dailyList.clear()
        val days = daily.skycon.size
        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val simpleDateFormat = SimpleDateFormat("M月d日", Locale.getDefault())
            val date = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            val Icon = sky.icon
            val Info = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
            val temperatureInfo = tempText
            dailyList.add(Daily(date, Icon, Info, temperatureInfo))
        }
    }
}
