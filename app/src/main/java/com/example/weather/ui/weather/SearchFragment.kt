package com.example.weather.ui.weather

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weather.R
import com.example.weather.adapter.SearchAdapter
import com.example.weather.databinding.FragmentSearchBinding
import com.example.weather.model.Search
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

class SearchFragment: Fragment(R.layout.fragment_search) {

    private val viewBinding: FragmentSearchBinding by viewBinding(FragmentSearchBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        viewBinding.e.addTextChangedListener {
            if (viewBinding.e.text.isNotBlank()){
                json(viewBinding.e.text.toString())
            }
        }

            viewBinding.btnS.setOnClickListener{
                if(viewBinding.e.text.isNotBlank()){
                    json(viewBinding.e.text.toString())
                }

            }






    }

    fun json(text: String){
        var url = "https://dataservice.accuweather.com/locations/v1/cities/autocomplete?apikey=w5vIvuGCGaORlx5myVMEkcJxkBAGBdjy&q=${text}&language=ru-ru"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error Connection", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                var body = response.body?.string()
                val gson = GsonBuilder().create()
                val searchToken = object : TypeToken<List<Search>>(){}.type
                var search = gson.fromJson<List<Search>>(body, searchToken)

                activity?.runOnUiThread {
                    viewBinding.searchRV.layoutManager = LinearLayoutManager(activity)
                    viewBinding.searchRV.adapter = SearchAdapter(search){
                        findNavController().navigate(R.id.action_searchFragment_to_temperatureFragment, bundleOf(
                            "key" to it
                        ))
                    }

                }
            }

        })

    }



}