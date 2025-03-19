package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.databinding.GetStartedBinding

class GetStarted : Fragment() {
    private  var binding :GetStartedBinding ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GetStartedBinding.inflate(inflater,container,false)

        binding?.getStartedStartBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_started_login)
        }

        return binding?.root
    }

}