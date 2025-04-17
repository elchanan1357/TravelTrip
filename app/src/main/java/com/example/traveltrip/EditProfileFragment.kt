package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.databinding.EditProfileBinding

class EditProfileFragment : Fragment() {
    private var binding: EditProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EditProfileBinding.inflate(inflater, container, false)

        binding?.editProfileCancelBtn?.setOnClickListener { findNavController().popBackStack() }
        binding?.editProfileSaveBtn?.setOnClickListener {

            findNavController().popBackStack()
        }

        return binding?.root
    }

}