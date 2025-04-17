package com.example.traveltrip

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import at.favre.lib.crypto.bcrypt.BCrypt
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

        binding?.LoginBtn?.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val user = handleLogin()
                if (user != null) {
                    findNavController().navigate(R.id.action_login_home)
                }
            }
        }
        binding?.SignupBtn?.setOnClickListener { findNavController().navigate(R.id.action_login_register) }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private suspend fun handleLogin(): User? {
        var checking = isNull(binding?.email)
        checking = isNull(binding?.password) || checking

        if (checking) return null

        var user: User? = null
        val email = binding?.email?.text.toString()
        val pass = binding?.password?.text.toString()

        return suspendCoroutine { continuation ->
            ModelUser.instance.getUserByEmail(email) { user ->
                if (user == null) {
                    log("User not found")
                    continuation.resume(null)
                } else {
                    ModelUser.instance.setEmail(email)
                    BCrypt.verifyer()
                        .verify(pass.toCharArray(), user.password.toCharArray()).verified
                    continuation.resume(user)
                }
            }
        }
    }

}