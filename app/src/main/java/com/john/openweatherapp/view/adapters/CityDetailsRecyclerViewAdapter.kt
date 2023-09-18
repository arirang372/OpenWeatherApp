package com.john.openweatherapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.john.openweatherapp.databinding.CityNameListItemBinding
import com.john.openweatherapp.model.GeocodingDetails
import com.john.openweatherapp.view.SearchWeatherCallback

class CityDetailsRecyclerViewAdapter(private val callback: SearchWeatherCallback) :
    RecyclerView.Adapter<CityDetailsRecyclerViewAdapter.CityDetailsViewHolder>() {

    private var cityDetails: MutableList<GeocodingDetails> = mutableListOf()

    class CityDetailsViewHolder(val binding: CityNameListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityDetailsViewHolder {
        val binding =
            CityNameListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityDetailsViewHolder(binding)
    }

    override fun getItemCount() = cityDetails.size

    override fun onBindViewHolder(holder: CityDetailsViewHolder, position: Int) {
        holder.binding.model = cityDetails[position]
        holder.binding.callback = callback
        holder.binding.executePendingBindings()
    }

    fun setCityDetails(cityDetails: List<GeocodingDetails>) {
        this.cityDetails.clear()
        this.cityDetails.addAll(cityDetails)
        notifyDataSetChanged()
    }
}