package com.example.traveltrip.ui.fragments.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.R
import com.example.traveltrip.databinding.ProfileBinding
import com.example.traveltrip.model.room.entity.User
import com.example.traveltrip.ui.viewModel.UserViewModel
import com.example.traveltrip.utils.createToast
import com.example.traveltrip.utils.getPicFromPicasso
import com.example.traveltrip.utils.logError

class ProfileFragment : Fragment() {
    private var binding: ProfileBinding? = null
    private var _user: User? = null
    private var viewModel: UserViewModel? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileBinding.inflate(inflater, container, false)

        observeError()
        observeUser()
        observeSuccess()

        binding?.editDetailsBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_profile_editProfile)
        }
        binding?.yourPostsBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_proflie_myPosts)
        }

        return binding?.root
    }


    override fun onResume() {
        super.onResume()
        viewModel?.getCurrentUser()
        binding?.deleteBtn?.setOnClickListener { handleDelete() }
        binding?.logOutBtn?.setOnClickListener {
            binding?.progressBar?.visibility = View.VISIBLE
            viewModel?.signOut()
        }
    }


    private fun handleDelete() {
        binding?.progressBar?.visibility = View.VISIBLE
        if (this._user != null) {
            viewModel?.deleteUser(_user!!)
        } else logError("Not find user")
    }


    override fun onDestroy() {
        super.onDestroy()
        this.binding = null
    }


    private fun observeError() {
        viewModel?.errorMessage?.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding?.progressBar?.visibility = View.GONE
                createToast(error)
            }
        }
    }


    private fun observeUser() {
        viewModel?.user?.observe(viewLifecycleOwner) { user ->
            user?.let {
                this._user = user
                getPicFromPicasso(binding?.img, user.img)

                binding?.name?.text = user.name
                binding?.phone?.text = user.phone
                binding?.email?.text = user.email
                binding?.password?.text = user.password
            }
        }
    }


    private fun observeSuccess() {
        viewModel?.isSuccess?.observe(viewLifecycleOwner) {
            if (it) {
                binding?.progressBar?.visibility = View.GONE
                findNavController().navigate(
                    R.id.getStarted,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.nav_graph, true)
                        .setLaunchSingleTop(true)
                        .build()
                )
            }
        }
    }

}