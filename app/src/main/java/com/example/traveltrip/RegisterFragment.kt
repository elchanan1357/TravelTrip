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
import com.example.traveltrip.databinding.RegisterBinding
import com.example.traveltrip.model.ModelUser
import com.example.traveltrip.model.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragment : Fragment() {
    private var binding: RegisterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterBinding.inflate(inflater, container, false)

        binding?.SigninBtn?.setOnClickListener { findNavController().popBackStack() }
        binding?.SignupBtn?.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val user: User? = handleSignup()
                if (user != null) {
                    withContext(Dispatchers.IO) {
                        ModelUser.instance.addUser(user) {
                            findNavController().popBackStack()
                        }
                    }
                }
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

        if (checking) {
            log("You must fill in all the details.")
            return null
        }

        val pass1 = binding?.password?.text.toString()
        val pass2 = binding?.password2?.text.toString()

        if (pass1 != pass2) {
            log("the password not same")
            return null
        }

        val isChecked = binding?.CheckBox?.isChecked ?: false
        if (isChecked) {
            log("you must the a prov checkbox")
            return null
        }

        val hashedPassword = BCrypt.withDefaults().hashToString(10, pass1.toCharArray())

        return User(
            binding?.name?.text.toString(),
            binding?.phone?.text.toString(),
            binding?.email?.text.toString(),
            hashedPassword
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

