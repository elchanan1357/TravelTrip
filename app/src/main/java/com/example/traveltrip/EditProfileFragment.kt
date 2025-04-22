package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.utils.isNull
import com.example.traveltrip.utils.log
import com.example.traveltrip.utils.logError
import com.example.traveltrip.databinding.EditProfileBinding
import com.example.traveltrip.model.ModelUser
import com.example.traveltrip.model.entity.User

class EditProfileFragment : Fragment() {
    private var binding: EditProfileBinding? = null
    private var _user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EditProfileBinding.inflate(inflater, container, false)
        displayUser()

        binding?.editProfileCancelBtn?.setOnClickListener { pop() }
        binding?.editProfileSaveBtn?.setOnClickListener { handleSave() }

        return binding?.root
    }

    private fun displayUser() {
        val email = ModelUser.instance.getEmail()

        if (email != null)
            ModelUser.instance.getUserByEmail(email) { user ->
                if (user != null) {
                    this._user = user
                    binding?.editProfileName?.setText(user.name)
                    binding?.editProfilePhone?.setText(user.phone)
                    binding?.editProfileEmail?.setText(user.email)
                    binding?.editProfilePassword?.setText(user.password)

                    binding?.editProfileEmail?.isEnabled = false
                    binding?.editProfilePassword?.isEnabled = false

                } else logError("Not find User")

            }
    }

    private fun handleSave() {
        var checking = isNull(binding?.editProfileName)
        checking = isNull(binding?.editProfilePhone) || checking
        checking = isNull(binding?.editProfileEmail) || checking
        checking = isNull(binding?.editProfilePassword) || checking

        if (checking) {
            log("please provide all data")
            return
        }

        val user = _user
        user?.name = binding?.editProfileName?.text.toString()
        user?.phone = binding?.editProfilePhone?.text.toString()


        if (user != null) ModelUser.instance.updateUser(user) { pop() }
    }

    private fun pop() {
        findNavController().popBackStack()
    }

}