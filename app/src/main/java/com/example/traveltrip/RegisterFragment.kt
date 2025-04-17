package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.databinding.RegisterBinding

class RegisterFragment : Fragment() {
    private var binding: RegisterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterBinding.inflate(inflater, container, false)

        binding?.SigninBtn?.setOnClickListener { switchToLogin() }
        binding?.SignupBtn?.setOnClickListener {
//            val user: User? = null
//            user?.name = binding?.name?.text.toString()
//            user?.phone = binding?.phone.toString()
//            user?.password = binding?.password.toString()


            switchToLogin()


        }

        return binding?.root
    }

    private fun switchToLogin() {
        findNavController().navigate(R.id.action_register_login)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

