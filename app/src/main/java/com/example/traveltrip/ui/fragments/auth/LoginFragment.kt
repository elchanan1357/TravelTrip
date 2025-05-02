package com.example.traveltrip.ui.fragments.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.R
import com.example.traveltrip.utils.log
import com.example.traveltrip.databinding.LoginBinding
import com.example.traveltrip.ui.viewModel.UserViewModel
import com.example.traveltrip.utils.FieldValidation
import com.example.traveltrip.utils.createToast
import com.example.traveltrip.utils.validateFields

class LoginFragment : Fragment() {
    private var binding: LoginBinding? = null
    private var viewModel: UserViewModel? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginBinding.inflate(inflater, container, false)

        binding?.LoginBtn?.setOnClickListener { handleLogin() }
        binding?.SignupBtn?.setOnClickListener { findNavController().navigate(R.id.action_login_register) }

        observeSuccess()
        observeError()

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

        binding?.progressBar?.visibility = View.VISIBLE
        val email = binding?.email?.text.toString()
        val pass = binding?.password?.text.toString()

        viewModel?.signIn(email, pass)

    }

    private fun observeError() {
        viewModel?.errorMessage?.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding?.progressBar?.visibility = View.GONE
                createToast(error)
            }
        }
    }

    private fun observeSuccess() {
        viewModel?.isSuccess?.observe(viewLifecycleOwner) {
            if (it) {
                binding?.progressBar?.visibility = View.GONE
                findNavController().navigate(R.id.action_login_home)
            }
        }
    }

}