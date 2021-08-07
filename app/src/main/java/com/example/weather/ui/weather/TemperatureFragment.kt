package com.example.weather.ui.weather

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.HorizontalScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.weather.R
import com.example.weather.adapter.FiveDaysAdapter
import com.example.weather.databinding.FragmentTemperatureBinding
import com.example.weather.model.Search
import com.example.weather.model.Temp
import com.example.weather.model.Weather
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class TemperatureFragment: Fragment(R.layout.fragment_temperature) {

    private val viewBinding: FragmentTemperatureBinding by viewBinding(FragmentTemperatureBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        (arguments?.getSerializable("key") as? Weather)?.let { weather ->
            json(weather.Key, weather.LocalizedName)
            daily(weather.Key)

        }


    }

    fun json(key: String, city: String){
        val url =
            "http://dataservice.accuweather.com/forecasts/v1/daily/1day/$key?apikey=GEwt65US7sbGh21KILwgo36AGGke5WnN&language=ru-ru"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Connection error", e.toString())
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                var temperature = gson.fromJson(body, Temp::class.java)

                activity?.runOnUiThread {
                    viewBinding.day.text = "Днем: ${temperature.DailyForecasts?.firstOrNull()?.Day?.IconPhrase}"
                    viewBinding.night.text =  "Ночью: ${temperature.DailyForecasts?.firstOrNull()?.Night?.IconPhrase}"
                    viewBinding.maxMin.text = "${temperature.DailyForecasts?.firstOrNull()?.Temperature?.Minimum?.valueInC}°/${temperature.DailyForecasts?.firstOrNull()?.Temperature?.Maximum?.valueInC}°"
                    viewBinding.tbCity.title = city
                    viewBinding.status.text = temperature.Headline.Text
                    viewBinding.temperature.text = "${temperature.DailyForecasts?.firstOrNull()?.Temperature?.Minimum?.valueInC}°C"
                }

            }

        })
    }

    fun daily(key: String){
        val url = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/$key?apikey=GEwt65US7sbGh21KILwgo36AGGke5WnN&language=ru-ru"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("CONNECTION ERROR", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                var temp = gson.fromJson(body, Temp::class.java)

                activity?.runOnUiThread {
                    viewBinding.fiveDay.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    viewBinding.fiveDay.adapter = FiveDaysAdapter(temp)
                }

            }

        })
    }
}