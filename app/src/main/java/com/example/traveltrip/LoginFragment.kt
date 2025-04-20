package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.traveltrip.Utils.isNull
import com.example.traveltrip.Utils.log
import com.example.traveltrip.Utils.logError
import com.example.traveltrip.databinding.LoginBinding
import com.example.traveltrip.model.ModelUser
import com.example.traveltrip.model.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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
        var checking = isNull(binding?.email)
        checking = isNull(binding?.password) || checking

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