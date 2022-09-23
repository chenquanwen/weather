package com.example.a0720.UI.Weather

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a0720.R
import com.example.a0720.logic.Model.Hourly

class ForecastHourlyAdapter(val context: Context, val list: ArrayList<Hourly>): RecyclerView.Adapter<ForecastHourlyAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val time: TextView = view.findViewById(R.id.time)
        val hourlyTemp: TextView = view.findViewById(R.id.hourlyTemp)
        val hourlyIcon: ImageView = view.findViewById(R.id.hourlyImg)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.forecasthourly_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastHourlyAdapter.ViewHolder, position: Int) {
        val position = list[position]
        holder.time.text = position.time
        holder.hourlyTemp.text = position.tempertaure
        Glide.with(context).load(position.icon).into(holder.hourlyIcon)
    }

    override fun getItemCount() = list.size
}