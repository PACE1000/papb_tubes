package com.example.papb_tubes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.papb_tubes.databinding.ItemRowBinding

class WeatherAdapter(
    private val onClickItem: OnClickListener // Use the correct interface name here
) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<WeatherResponse>() {
        override fun areItemsTheSame(oldItem: WeatherResponse, newItem: WeatherResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: WeatherResponse,
            newItem: WeatherResponse):
                Boolean = oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, differCallback)

    fun submitData(value: List<WeatherResponse>?) = differ.submitList(value)

    inner class ViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: WeatherResponse) {
            val temp = (data.main.temp) - 273
            binding.apply {
                tvNamakota.text = data.name
                tvTemperatur.text = temp.toString()
                tvTipe.text = data.weather[0].description

                root.setOnClickListener {
                    onClickItem.onClickItem(data)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]

        data.let { holder.bind(data) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    interface OnClickListener {
        fun onClickItem(data: WeatherResponse)
    }
}
