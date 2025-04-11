package com.example.traveltrip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.traveltrip.R
import com.example.traveltrip.model.Travel

class AllTripsAdapter(private val trips: MutableList<Travel>?) :
    RecyclerView.Adapter<AllTripsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTripsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_trip, parent, false)
        return AllTripsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AllTripsViewHolder, position: Int) {
        holder.bind(trips?.get(position), position)
    }

    override fun getItemCount(): Int = trips?.size ?: 0
}


