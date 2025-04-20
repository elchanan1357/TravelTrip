package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.databinding.GetStartedBinding

class GetStartedFragment : Fragment() {
    private  var binding :GetStartedBinding ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GetStartedBinding.inflate(inflater,container,false)

        binding?.StartBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_started_login)
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}