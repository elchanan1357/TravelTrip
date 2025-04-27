package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.utils.log
import com.example.traveltrip.databinding.LoginBinding
import com.example.traveltrip.model.ModelUser
import com.example.traveltrip.utils.FieldValidation
import com.example.traveltrip.utils.validateFields

class LoginFragment : Fragment() {
    private var binding: LoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginBinding.inflate(inflater, container, false);

        binding?.LoginBtn?.setOnClickListener { handleLogin() }
        binding?.SignupBtn?.setOnClickListener { findNavController().navigate(R.id.action_login_register) }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun handleLogin() {
        val validation = arrayOf(
            FieldValidation(binding?.email, "You must provide your email"),
            FieldValidation(binding?.password, "You must provide your password")
        )

        if (!validateFields(*validation)) {
            log("please provide me all data in login")
            return
        }

        val email = binding?.email?.text.toString()
        binding?.progressBar?.visibility = View.VISIBLE
        ModelUser.instance.getUserByEmail(email) { user ->
            binding?.progressBar?.visibility = View.GONE
            if (user == null) {
                log("login: User not found")
            } else {
                findNavController().navigate(R.id.action_login_home)
            }
        }
    }
}