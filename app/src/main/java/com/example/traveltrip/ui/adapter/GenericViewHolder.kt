package com.example.traveltrip.ui.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class GenericViewHolder<T, VB : ViewBinding>(
    val binding: VB,
    private val bindItem: (VB, T) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T?, position: Int) {
        item?.let { bindItem(binding, it) } ?: Log.d("logs", "Fail: item is null")
    }
}

