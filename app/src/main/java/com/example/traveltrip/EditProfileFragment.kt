package com.example.traveltrip

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.utils.log
import com.example.traveltrip.utils.logError
import com.example.traveltrip.databinding.EditProfileBinding
import com.example.traveltrip.model.ModelUser
import com.example.traveltrip.model.entity.User
import com.example.traveltrip.utils.FieldValidation
import com.example.traveltrip.utils.launchCameraForImage
import com.example.traveltrip.utils.validateFields

class EditProfileFragment : Fragment() {
    private var binding: EditProfileBinding? = null
    private var _user: User? = null
    private var _bitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EditProfileBinding.inflate(inflater, container, false)

        launchCameraForImage(this, binding?.imgProfile, binding?.clickImg) {
            this._bitmap = it
        }

        return binding?.root
    }

    override fun onStart() {
        super.onStart()

        displayUser()
        binding?.cancelBtn?.setOnClickListener { pop() }
        binding?.saveBtn?.setOnClickListener { handleSave() }
    }

    private fun displayUser() {
        val email = ModelUser.instance.getEmail()

        if (email != null)
            ModelUser.instance.getUserByEmail(email) { user ->
                if (user != null) {
                    this._user = user
                    binding?.name?.setText(user.name)
                    binding?.phone?.setText(user.phone)
                    binding?.email?.setText(user.email)
                    binding?.password?.setText(user.password)

                    binding?.email?.isEnabled = false
                    binding?.password?.isEnabled = false

                } else logError("Not find User")
            }
    }


    private fun handleSave() {
        val validation = arrayOf(
            FieldValidation(binding?.name, "Enter your city"),
            FieldValidation(binding?.phone, "Enter your state"),
            FieldValidation(binding?.email, "Enter the title"),
            FieldValidation(binding?.password, "Enter the text"),
        )

        if (!validateFields(*validation)) {
            log("please provide me all data")
            return
        }

        val user = _user
        user?.name = binding?.name?.text.toString()
        user?.phone = binding?.phone?.text.toString()

        if (user?.phone == _user?.phone && user?.name == _user?.name)
            return

        user?.let { ModelUser.instance.updateUser(user) { pop() } }
    }


    private fun pop() {
        findNavController().popBackStack()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}