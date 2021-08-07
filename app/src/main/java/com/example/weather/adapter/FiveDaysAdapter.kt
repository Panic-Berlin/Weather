package com.example.weather.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weather.R
import com.example.weather.databinding.FiveDaysItemBinding
import com.example.weather.model.DailyForecasts
import com.example.weather.model.Temp

class FiveDaysAdapter(val temperature: Temp): RecyclerView.Adapter<FiveDaysAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForDay = layoutInflater.inflate(R.layout.five_days_item, parent, false)
        return CustomViewHolder(cellForDay)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(temperature.DailyForecasts[position])
    }

    override fun getItemCount(): Int {
        return temperature.DailyForecasts.count()
    }

    class CustomViewHolder(view: View): RecyclerView.ViewHolder(view){

        val viewBinding: FiveDaysItemBinding by viewBinding(FiveDaysItemBinding::bind)

        @SuppressLint("SetTextI18n")
        fun bind(temperature: DailyForecasts){
            viewBinding.dayMax.text = "${temperature.Temperature.Maximum.valueInC}°"
            viewBinding.dayMin.text = "${temperature.Temperature.Minimum.valueInC}°"
        }
    }
}