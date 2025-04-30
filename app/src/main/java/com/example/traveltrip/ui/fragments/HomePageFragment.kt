package com.example.traveltrip.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.R
import com.example.traveltrip.databinding.HomePageBinding
import com.example.traveltrip.model.room.models.RoomUser
import com.example.traveltrip.ui.viewModel.UserViewModel
import com.example.traveltrip.utils.logError


class HomePageFragment : Fragment() {
    private var binding: HomePageBinding? = null
    private var viewModel: UserViewModel? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomePageBinding.inflate(inflater, container, false)

        binding?.BlogIcon?.setOnClickListener { findNavController().navigate(R.id.action_home_blogs) }
        binding?.DiscoverIcon?.setOnClickListener { findNavController().navigate(R.id.action_home_discover) }
        binding?.TripsIcon?.setOnClickListener { findNavController().navigate(R.id.action_home_trips) }


        observeError()
        observeUser()
        viewModel?.getUSer()

        return binding?.root
    }

    private fun observeError() {
        viewModel?.errorMessage?.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun observeUser() {
        viewModel?.user?.observe(viewLifecycleOwner) {
            it?.let {
                binding?.name?.text = "Hi ${it.name}, Welcome"
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
