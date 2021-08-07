package com.example.weather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weather.R
import com.example.weather.databinding.SearchItemBinding
import com.example.weather.model.Search

class SearchAdapter(
    private val search: List<Search>,
    private val onCityClick: (search : Search) -> Unit

    ): RecyclerView.Adapter<SearchAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForSearch = layoutInflater.inflate(R.layout.search_item, parent, false)
        return CustomViewHolder(cellForSearch)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(search[position])
        holder.itemView.setOnClickListener{
            onCityClick.invoke(search[position])
        }

    }

    override fun getItemCount(): Int {
        return search.count()
    }

    class CustomViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val viewBinding: SearchItemBinding by viewBinding(SearchItemBinding::bind)
        private var KEY: String = ""

        fun bind(search: Search){
            viewBinding.city.text = search.LocalizedName
            KEY = search.Key
        }
    }
}