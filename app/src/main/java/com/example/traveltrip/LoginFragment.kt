package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.traveltrip.utils.isNull
import com.example.traveltrip.utils.log
import com.example.traveltrip.utils.logError
import com.example.traveltrip.databinding.LoginBinding
import com.example.traveltrip.model.ModelUser

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
        val checking = isNull(binding?.email)
                || isNull(binding?.password)

        if (checking) return

        val email = binding?.email?.text.toString()
        val pass = binding?.password?.text.toString()

        ModelUser.instance.getUserByEmail(email) { user ->
            if (user == null) {
                log("login: User not found")
            } else {
                ModelUser.instance.setEmail(email)

                val res = BCrypt.verifyer()
                    .verify(pass.toCharArray(), user.password.toCharArray()).verified
                if (res) findNavController().navigate(R.id.action_login_home)
                else logError("login: The password not correct")
            }
        }
    }
}