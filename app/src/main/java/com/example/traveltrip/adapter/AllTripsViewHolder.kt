package com.example.traveltrip.adapter

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.traveltrip.R
import com.example.traveltrip.model.Travel


class AllTripsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var title: TextView? = null
    private var info: TextView? = null
    private var img: ImageButton? = null

    init {
        this.title = itemView.findViewById(R.id.rowTrip_title)
        this.info = itemView.findViewById(R.id.rowTrip_information)
        this.img = itemView.findViewById(R.id.rowTrip_imgBtn)
    }

    fun bind(trips: Travel?, position: Int) {
        this.title?.text = trips?.title
        this.info?.text = trips?.info
        trips?.img?.let { imageUri ->
            this.img?.setImageURI(Uri.parse(imageUri))
        }
        Log.d("Adapter","${trips?.img}")
    }
}