package com.example.traveltrip.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.R
import com.example.traveltrip.utils.log
import com.example.traveltrip.databinding.ProfileBinding
import com.example.traveltrip.model.room.models.RoomUser
import com.example.traveltrip.model.room.entity.User
import com.example.traveltrip.utils.getPicFromPicasso
import com.example.traveltrip.utils.logError

class ProfileFragment : Fragment() {
    private var binding: ProfileBinding? = null
    private var _user: User? = null
    val email = RoomUser.instance.getEmail() ?: ""

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
            RoomUser.instance.deleteUser(this._user!!) {
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
        RoomUser.instance.getUserByEmail(email) { user ->
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