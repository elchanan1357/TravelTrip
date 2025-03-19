package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class RegisterFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.register, container, false)

        view.findViewById<Button>(R.id.register_signupBtn).setOnClickListener { switchToLogin() }
        view.findViewById<Button>(R.id.register_signinBtn).setOnClickListener { switchToLogin() }

        return view
    }

    private fun switchToLogin() {
        findNavController().navigate(R.id.action_register_login)
    }
}

