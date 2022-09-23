package com.example.a0720.UI.Weather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a0720.R
import com.example.a0720.logic.Model.Daily

class ForecastDailyAdapter(private val context: Context, private val dailyList: List<Daily>) : RecyclerView.Adapter<ForecastDailyAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val temp: TextView = view.findViewById(R.id.DailyTemp)
        val day: TextView = view.findViewById(R.id.Day)
        val icon: ImageView = view.findViewById(R.id.DayIcon)
        val info: TextView = view.findViewById(R.id.dayInfo)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.forecastdaily_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dailyList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val position = dailyList[position]
        holder.day.text = position.date
        holder.temp.text = position.tempertaure
        Glide.with(context).load(position.skyIcon).into(holder.icon)
        holder.info.text = position.skyInfo
    }


}

