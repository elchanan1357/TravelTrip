package com.example.traveltrip.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.MainActivity
import com.example.traveltrip.R
import com.example.traveltrip.databinding.GetStartedBinding
import com.example.traveltrip.ui.viewModel.UserViewModel

class GetStartedFragment : Fragment() {
    private var binding: GetStartedBinding? = null
    private var viewModel: UserViewModel? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        viewModel?.getCurrentUser()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GetStartedBinding.inflate(inflater, container, false)

        observeError()
        observeUser()


        binding?.StartBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_started_login)
        }

        return binding?.root
    }


    private fun observeError() {
        viewModel?.errorMessage?.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun observeUser() {
        viewModel?.user?.observe(viewLifecycleOwner) {
            it?.let {
                (activity as? MainActivity)?.switchToNavHostFragment(R.id.homePageFragment)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}