package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.utils.log
import com.example.traveltrip.databinding.ProfileBinding
import com.example.traveltrip.model.ModelUser

class ProfileFragment : Fragment() {
    private var binding: ProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileBinding.inflate(inflater, container, false)

        binding?.editDetailsBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_profile_editProfile)
        }

        binding?.yourPostsBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_proflie_myPosts)
        }
        displayData()

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        this.binding = null
    }

    private fun displayData() {
        val email = ModelUser.instance.getEmail()

        if (email != null)
            ModelUser.instance.getUserByEmail(email) { user ->
                if (user != null) {
                    binding?.name?.text = user.name
                    binding?.phone?.text = user.phone
                    binding?.email?.text = user.email
                    binding?.password?.text = user.password
                } else log("not find user")
            }
    }

}