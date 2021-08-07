package com.example.weather.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weather.R
import com.example.weather.databinding.WeatherItemBinding
import com.example.weather.model.Weather

class WeatherAdapter(
    private val weather: List<Weather>,
    private val onCityClick: (weather : Weather) -> Unit
    ): RecyclerView.Adapter<WeatherAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForWeather = layoutInflater.inflate(R.layout.weather_item, parent, false)
        return CustomViewHolder(cellForWeather)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(weather[position])
        holder.itemView.setOnClickListener {
            onCityClick.invoke(weather[position])
        }
    }

    override fun getItemCount(): Int {
        return weather.count()
    }

    class CustomViewHolder(view: View): RecyclerView.ViewHolder(view){

        val viewBinding: WeatherItemBinding by viewBinding(WeatherItemBinding::bind)

        @SuppressLint("SetTextI18n")
        fun bind(weather: Weather){
            viewBinding.countryName.text = "Страна: ${weather.Country.LocalizedName}"
            viewBinding.localizedName.text = "Город: ${weather.LocalizedName}"
            viewBinding.regionName.text = "Регион: ${weather.AdministrativeArea.LocalizedName}"
        }
    }
}