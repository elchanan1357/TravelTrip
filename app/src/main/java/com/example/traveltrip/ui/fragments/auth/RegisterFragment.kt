package com.example.traveltrip.ui.fragments.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.utils.log
import com.example.traveltrip.databinding.RegisterBinding
import com.example.traveltrip.model.room.entity.User
import com.example.traveltrip.ui.viewModel.UserViewModel
import com.example.traveltrip.utils.FieldValidation
import com.example.traveltrip.utils.createToast
import com.example.traveltrip.utils.validateFields

class RegisterFragment : Fragment() {
    private var binding: RegisterBinding? = null
    private var viewModel: UserViewModel? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterBinding.inflate(inflater, container, false)

        binding?.SigninBtn?.setOnClickListener { findNavController().popBackStack() }
        binding?.SignupBtn?.setOnClickListener { handleSignup() }

        observeSuccess()
        observeError()

        return binding?.root
    }

    private fun handleSignup() {
        if (!checkDataIsValidate()) {
            log("You must fill in all the details.")
            return
        }

        val pass1 = binding?.password?.text.toString()
        val pass2 = binding?.password2?.text.toString()

        if (pass1 != pass2) {
            binding?.password?.error = "The password not same"
            binding?.password2?.error = "The password not same"
            log("the password not same")
            return
        }

        val isChecked = binding?.CheckBox?.isChecked ?: false
        if (!isChecked) {
            binding?.CheckBox?.error = "Must confirm"
            log("you must the a prov checkbox")
            return
        }

        val user = User(
            binding?.name?.text.toString(),
            binding?.phone?.text.toString(),
            binding?.email?.text.toString(),
            binding?.password?.text.toString()
        )

        binding?.progressBar?.visibility = View.VISIBLE

        viewModel?.insertUSer(user)
    }

    private fun checkDataIsValidate(): Boolean {
        val validation = arrayOf(
            FieldValidation(binding?.name, "Enter your name"),
            FieldValidation(binding?.phone, "Enter your phone number"),
            FieldValidation(binding?.email, "Enter your email address"),
            FieldValidation(binding?.password, "Enter a password"),
            FieldValidation(binding?.password2, "Confirm your password")
        )

        if ((binding?.password?.text?.length ?: 0) < 6) {
            binding?.password?.error = "Password should be at least 6 characters"
            return false
        }


        return validateFields(*validation)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
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
                findNavController().popBackStack()
            }
        }
    }
}

