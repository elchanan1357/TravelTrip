package com.example.traveltrip

import android.annotation.SuppressLint
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import androidx.navigation.NavOptions
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.Utils.log
import com.example.traveltrip.databinding.HomePageBinding
import com.example.traveltrip.model.ModelUser
import com.example.traveltrip.model.entity.User


class HomePageFragment : Fragment() {
    private var binding: HomePageBinding? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomePageBinding.inflate(inflater, container, false)

        binding?.BlogIcon?.setOnClickListener { findNavController().navigate(R.id.action_home_blogs) }
        binding?.DiscoverIcon?.setOnClickListener { findNavController().navigate(R.id.action_home_discover) }
//         binding?.homeTripsIcon?.setOnClickListener { findNavController().navigate(R.id.action_home_trips) }

        val email: String? = ModelUser.instance.getEmail()
        if (email != null)
            ModelUser.instance.getUserByEmail(email) { user ->
                if (user != null)
                    binding?.name?.text = "Hi ${user.name}, Welcome"
            }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
