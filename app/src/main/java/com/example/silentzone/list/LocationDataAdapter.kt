package com.example.silentzone.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.silentzone.location_database.LocationData
import com.example.silentzone.databinding.CustomRowBinding

class LocationDataAdapter: ListAdapter<LocationData,LocationDataAdapter.LocationDataViewHolder>(DiffCallback()) {
    class LocationDataViewHolder(private val binding: CustomRowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(locationData: LocationData){
            binding.rowNameTextView.text=locationData.name
            binding.rowLatitudeTextView.text=locationData.latitude.toString()
            binding.rowLongitudeTextView.text=locationData.longitude.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationDataViewHolder {
        val binding=CustomRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LocationDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationDataViewHolder, position: Int) {
        val currentItem=getItem(position)
        holder.bind(currentItem)
        holder.itemView.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }


    }

    class DiffCallback: DiffUtil.ItemCallback<LocationData>(){
        override fun areItemsTheSame(oldItem: LocationData, newItem: LocationData): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: LocationData, newItem: LocationData): Boolean {
            return oldItem==newItem
        }

    }
}