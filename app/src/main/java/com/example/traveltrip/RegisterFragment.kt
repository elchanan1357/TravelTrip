package com.example.traveltrip

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.databinding.RegisterBinding
import com.example.traveltrip.model.ModelUser
import com.example.traveltrip.model.entity.User

class RegisterFragment : Fragment() {
    private var binding: RegisterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterBinding.inflate(inflater, container, false)

        binding?.SigninBtn?.setOnClickListener { switchToLogin() }
        binding?.SignupBtn?.setOnClickListener {
            val user: User? = handleSignup()
            if (user != null)
                ModelUser.instance.addUser(user) {
                    ModelUser.instance.getAllUsers {}
                    switchToLogin()
                }
        }

        return binding?.root
    }

    private fun handleSignup(): User? {
        var checking: Boolean = isNull(binding?.name)
        checking = isNull(binding?.phone) || checking
        checking = isNull(binding?.email) || checking
        checking = isNull(binding?.password) || checking
        checking = isNull(binding?.password2) || checking

        if (checking) return null

        val pass1 = binding?.password?.text.toString()
        val pass2 = binding?.password2?.text.toString()

        if (pass1 != pass2) return null

        return User(
            binding?.name?.text.toString(),
            binding?.phone?.text.toString(),
            binding?.email?.text.toString(),
            binding?.password?.text.toString()
        )
    }

    private fun isNull(et: EditText?): Boolean {
        if (et?.text.isNullOrBlank()) {
            et?.error = "This filed is required"
            return true
        }

        return false
    }

    private fun switchToLogin() {
        findNavController().navigate(R.id.action_register_login)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

