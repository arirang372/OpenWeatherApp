package com.john.openweatherapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.john.openweatherapp.databinding.WeatherDataListItemBinding
import com.john.openweatherapp.model.WeatherInfo

class WeatherDataRecyclerViewAdapter :
    RecyclerView.Adapter<WeatherDataRecyclerViewAdapter.WeatherDataViewHolder>() {

    private var weatherInfos: MutableList<WeatherInfo> = mutableListOf()

    class WeatherDataViewHolder(val binding: WeatherDataListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDataViewHolder {
        val binding =
            WeatherDataListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherDataViewHolder(binding)
    }

    override fun getItemCount() = weatherInfos.size

    override fun onBindViewHolder(holder: WeatherDataViewHolder, position: Int) {
        holder.binding.model = weatherInfos[position]
        holder.binding.executePendingBindings()
    }

    fun setWeatherInfo(weatherInfos: List<WeatherInfo>) {
        this.weatherInfos.clear()
        this.weatherInfos.addAll(weatherInfos)
        notifyDataSetChanged()
    }
}