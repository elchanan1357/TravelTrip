package com.example.traveltrip.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class GenericAdapter<T, VB: ViewBinding>(
    private var items: List<T>?,
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    private val bindItem: (VB, T) -> Unit
): RecyclerView.Adapter<GenericViewHolder<T, VB>>()  {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T, VB> {
            val binding = bindingInflater(LayoutInflater.from(parent.context), parent, false)
            return GenericViewHolder(binding, bindItem)
        }


        override fun onBindViewHolder(holder: GenericViewHolder<T, VB>, position: Int) {
            holder.bind(items?.get(position),position)
        }


        override fun getItemCount(): Int = items?.size ?: 0


        @SuppressLint("NotifyDataSetChanged")
        fun updateList(newList: List<T>?) {
            items = newList
            notifyDataSetChanged()
        }
}