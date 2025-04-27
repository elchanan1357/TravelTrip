package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.utils.log
import com.example.traveltrip.databinding.ProfileBinding
import com.example.traveltrip.model.ModelUser
import com.example.traveltrip.model.entity.User
import com.example.traveltrip.utils.getPicFromPicasso
import com.example.traveltrip.utils.logError

class ProfileFragment : Fragment() {
    private var binding: ProfileBinding? = null
    private var _user: User? = null
    val email = ModelUser.instance.getEmail() ?: ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileBinding.inflate(inflater, container, false)
        displayData()

        binding?.editDetailsBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_profile_editProfile)
        }
        binding?.yourPostsBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_proflie_myPosts)
        }
        binding?.deleteBtn?.setOnClickListener { handleDelete() }

        return binding?.root
    }

    private fun handleDelete() {
        binding?.progressBar?.visibility = View.VISIBLE
        if (this._user != null) {
            ModelUser.instance.deleteUser(this._user!!) {
                binding?.progressBar?.visibility = View.VISIBLE
                findNavController().navigate(
                    R.id.getStarted,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.nav_graph, true)
                        .setLaunchSingleTop(true)
                        .build()
                )
            }
        } else logError("Not find user")
    }

    override fun onDestroy() {
        super.onDestroy()
        this.binding = null
    }

    private fun displayData() {
        ModelUser.instance.getUserByEmail(email) { user ->
            this._user = user
            if (user != null) {
                getPicFromPicasso(binding?.img, user.img)

                binding?.name?.text = user.name
                binding?.phone?.text = user.phone
                binding?.email?.text = user.email
                binding?.password?.text = user.password
            } else log("not find user")
        }
    }

}