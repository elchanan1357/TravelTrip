package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.databinding.GetStartedBinding
import com.example.traveltrip.databinding.LoginBinding

class LoginFragment : Fragment() {
    private var binding: LoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginBinding.inflate(inflater, container, false);

        binding?.LoginBtn?.setOnClickListener { findNavController().navigate(R.id.action_login_home) }
        binding?.SignupBtn?.setOnClickListener { findNavController().navigate(R.id.action_login_register) }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}