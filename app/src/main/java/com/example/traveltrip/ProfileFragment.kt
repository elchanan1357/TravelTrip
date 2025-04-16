package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.traveltrip.databinding.ProfileBinding

class ProfileFragment : Fragment() {
    private var binding: ProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        this.binding = null
    }
}