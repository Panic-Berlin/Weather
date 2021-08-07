package com.example.weather.ui.weather

import android.content.Context
import android.hardware.input.InputManager
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.KeyboardShortcutGroup
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weather.MainActivity
import com.example.weather.R
import com.example.weather.adapter.SearchAdapter
import com.example.weather.adapter.WeatherAdapter
import com.example.weather.databinding.FragmentWeatherBinding
import com.example.weather.model.Search
import com.example.weather.model.Weather
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

class WeatherFragment: Fragment(R.layout.fragment_weather) {

    private val viewBinding: FragmentWeatherBinding by viewBinding(FragmentWeatherBinding::bind)
    private val API: String = "https://dataservice.accuweather.com/locations/v1/topcities/50?apikey="
    private val KEY: String = "GEwt65US7sbGh21KILwgo36AGGke5WnN"
    private val LANGUAGE: String = "&language=ru-ru"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.mainRv.layoutManager = LinearLayoutManager(context)

        viewBinding.want.addTextChangedListener {
            if (viewBinding.want.text.isNotBlank()){
                search(viewBinding.want.text.toString())
            }
        }

        viewBinding.btnSearchNav.setOnClickListener {
            if (!viewBinding.want.isVisible){
                viewBinding.want.visibility = View.VISIBLE
                viewBinding.want.requestFocus()
            }else{
                viewBinding.want.visibility = View.GONE
            }

        }


        json()



    }



    private fun json(){
        val url = API + KEY + LANGUAGE
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("CONNECTION_ERROR", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val weatherType = object : TypeToken<List<Weather>>(){}.type
                val weather = gson.fromJson<List<Weather>>(body, weatherType)

                activity?.runOnUiThread {
                    viewBinding.mainRv.adapter = WeatherAdapter(weather){


                    }
                }
            }

        })
    }


    fun search(text: String){
        var url = "https://dataservice.accuweather.com/locations/v1/cities/autocomplete?apikey=GEwt65US7sbGh21KILwgo36AGGke5WnN&q=${text}&language=ru-ru"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error Connection", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                var body = response.body?.string()
                val gson = GsonBuilder().create()
                val searchToken = object : TypeToken<List<Weather>>(){}.type
                var search = gson.fromJson<List<Weather>>(body, searchToken)

                activity?.runOnUiThread {
                    viewBinding.mainRv.layoutManager = LinearLayoutManager(activity)
                    viewBinding.mainRv.adapter = WeatherAdapter(search){
                        findNavController().navigate(R.id.action_weatherFragment_to_temperatureFragment, bundleOf(
                            "key" to it
                        )
                        )
                        viewBinding.want.text.clear()
                    }

                }
            }

        })

    }




}
