package com.example.binartujuh

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.binartujuh.databinding.ItemRowBinding

class WeatherAdapter(
    private val onItemClick: OnItemClickListener
):RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<AreasItem>() {
        override fun areItemsTheSame(oldItem: AreasItem, newItem: AreasItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AreasItem, newItem: AreasItem): Boolean {
            return oldItem == newItem
        }
    }
    private val differ=  AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: AreasItem) {
            binding.apply {
                tvNamakota.text = data.domain

            }
        }
        fun bindTime (data: Issue){
            binding.apply {
                tvTipe.text = data.day
            }
        }
        fun bindsuhu (data:TimesItem){
            binding.apply {
                tvTemperatur.text = data.celcius
            }
        }


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        when (data) {
            is AreasItem -> holder.bind(data)
            is Issue -> holder.bindTime(data)
            is TimesItem -> holder.bindsuhu(data)
            // Handle other types if needed
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size
    interface OnClickListener<T>{
        fun onClickItem(data: T)
    }
}

