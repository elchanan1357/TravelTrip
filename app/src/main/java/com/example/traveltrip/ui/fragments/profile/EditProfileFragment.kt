package com.example.traveltrip.ui.fragments.profile

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.R
import com.example.traveltrip.utils.log
import com.example.traveltrip.utils.logError
import com.example.traveltrip.databinding.EditProfileBinding
import com.example.traveltrip.model.room.entity.User
import com.example.traveltrip.ui.viewModel.UserViewModel
import com.example.traveltrip.utils.FieldValidation
import com.example.traveltrip.utils.getPicFromPicasso
import com.example.traveltrip.utils.launchCameraForImage
import com.example.traveltrip.utils.validateFields

class EditProfileFragment : Fragment() {
    private var binding: EditProfileBinding? = null
    private var _user: User? = null
    private var _bitmap: Bitmap? = null
    private var viewModel: UserViewModel? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EditProfileBinding.inflate(inflater, container, false)

        launchCameraForImage(this, binding?.imgProfile, binding?.clickImg) {
            this._bitmap = it
        }

        observeSuccess()
        observeError()
        observeUser()

        return binding?.root
    }

    override fun onStart() {
        super.onStart()

        viewModel?.getUSer()
        binding?.cancelBtn?.setOnClickListener {
            findNavController().popBackStack()
        }
        binding?.saveBtn?.setOnClickListener { handleSave() }
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

        binding?.progressBar?.visibility = View.VISIBLE
        val user = _user
        user?.name = binding?.name?.text.toString()
        user?.phone = binding?.phone?.text.toString()


        if (user?.phone == _user?.phone && user?.name == _user?.name && this._bitmap == null)
            return

        if (user != null) {
            viewModel?.updateUser(user, this._bitmap)
        }

    }


    private fun observeError() {
        viewModel?.errorMessage?.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
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

    private fun observeUser() {
        viewModel?.user?.observe(viewLifecycleOwner) { user ->
            user?.let {
                this._user = user
                binding?.name?.setText(user.name)
                binding?.phone?.setText(user.phone)
                binding?.email?.setText(user.email)
                binding?.password?.setText(user.password)
                if (user.img.isNotBlank())
                    getPicFromPicasso(binding?.imgProfile, user.img)

                binding?.email?.isEnabled = false
                binding?.password?.isEnabled = false
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}