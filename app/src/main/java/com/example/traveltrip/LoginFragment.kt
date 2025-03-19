package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login, container, false)

        view.findViewById<Button>(R.id.login_loginBtn).setOnClickListener {
            findNavController().navigate(R.id.action_login_home)
        }

        view.findViewById<Button>(R.id.login_signupBtn).setOnClickListener {
            findNavController().navigate(R.id.action_login_register)
        }

        return view
    }
}