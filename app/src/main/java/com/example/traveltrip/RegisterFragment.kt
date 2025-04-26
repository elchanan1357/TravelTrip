package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.utils.log
import com.example.traveltrip.databinding.RegisterBinding
import com.example.traveltrip.model.ModelUser
import com.example.traveltrip.model.entity.User
import com.example.traveltrip.utils.FieldValidation
import com.example.traveltrip.utils.validateFields

class RegisterFragment : Fragment() {
    private var binding: RegisterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterBinding.inflate(inflater, container, false)

        binding?.SigninBtn?.setOnClickListener { findNavController().popBackStack() }
        binding?.SignupBtn?.setOnClickListener { handleSignup() }

        return binding?.root
    }

    private fun handleSignup() {
        if (checkDataIsNull()) {
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
            log("you must the a prov checkbox")
            return
        }

        val user = User(
            binding?.name?.text.toString(),
            binding?.phone?.text.toString(),
            binding?.email?.text.toString(),
            binding?.password?.text.toString()
        )

        ModelUser.instance.addUser(user) {
            findNavController().popBackStack()
        }
    }

    private fun checkDataIsNull(): Boolean {
        val validation = arrayOf(
            FieldValidation(binding?.name, "Enter your name"),
            FieldValidation(binding?.phone, "Enter your phone number"),
            FieldValidation(binding?.email, "Enter your email address"),
            FieldValidation(binding?.password, "Enter a password"),
            FieldValidation(binding?.password2, "Confirm your password")
        )

        return validateFields(*validation)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

