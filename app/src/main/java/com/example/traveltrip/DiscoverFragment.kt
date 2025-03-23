package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.traveltrip.databinding.DiscoverBinding

class DiscoverFragment : Fragment() {
    private var binding: DiscoverBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DiscoverBinding.inflate(inflater, container, false)
        return binding?.root
    }
}