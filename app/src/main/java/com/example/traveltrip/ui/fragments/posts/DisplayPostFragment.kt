package com.example.traveltrip.ui.fragments.posts

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.databinding.DisplayPostBinding
import com.example.traveltrip.model.room.entity.User
import com.example.traveltrip.utils.getPicFromPicasso
import com.example.traveltrip.ui.viewModel.PostViewModel
import com.example.traveltrip.ui.viewModel.UserViewModel
import com.example.traveltrip.utils.createToast

class DisplayPostFragment : Fragment() {
    private var binding: DisplayPostBinding? = null
    private var viewModelPost: PostViewModel? = null
    private var viewModelUser: UserViewModel? = null
    private var _user: User? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModelPost = ViewModelProvider(this)[PostViewModel::class.java]
        viewModelUser = ViewModelProvider(this)[UserViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DisplayPostBinding.inflate(inflater, container, false)

        observePost()
        observeErrorPost()
        observeErrorUser()
        observeUser()
        binding?.progressBar?.visibility = View.VISIBLE
        viewModelUser?.getCurrentUser()

        return binding?.root
    }

    private fun observeErrorUser() {
        viewModelUser?.errorMessage?.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding?.progressBar?.visibility = View.GONE
                createToast(error)
            }
        }
    }


    private fun observeUser() {
        val postId = arguments?.getString("postID") ?: ""
        if (postId.isBlank()) {
            binding?.progressBar?.visibility = View.GONE
            createToast("Not find post")
            //TODO wait
            findNavController().popBackStack()
        }

        viewModelUser?.user?.observe(viewLifecycleOwner) {
            it?.let { user ->
                this._user = user
                viewModelPost?.getPostByID(postId)
            }
        }
    }


    private fun observeErrorPost() {
        viewModelPost?.errorMessage?.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding?.progressBar?.visibility = View.GONE
                createToast(error)
            }
        }
    }


    private fun observePost() {
        viewModelPost?.post?.observe(viewLifecycleOwner) { post ->
            if (post != null) {
                binding?.progressBar?.visibility = View.GONE
                getPicFromPicasso(binding?.imgPost, post.imgURI)
                //TODO fix
                binding?.name?.text = this._user?.name ?: "no user"
                binding?.city?.text = post.city
                binding?.state?.text = post.state
                binding?.title?.text = post.title
                binding?.text?.text = post.text
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}