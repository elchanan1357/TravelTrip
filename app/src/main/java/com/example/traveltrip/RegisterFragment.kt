package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.databinding.RegisterBinding

class RegisterFragment : Fragment() {
    private var binding: RegisterBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterBinding.inflate(inflater, container, false)

        binding?.registerSigninBtn?.setOnClickListener { switchToLogin() }
        binding?.registerSignupBtn?.setOnClickListener { switchToLogin() }

        return binding?.root
    }

    private fun switchToLogin() {
        findNavController().navigate(R.id.action_register_login)
    }
}

