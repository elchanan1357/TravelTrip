package com.example.traveltrip.ui.fragments.posts

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.traveltrip.databinding.DisplayPostBinding
import com.example.traveltrip.utils.getPicFromPicasso
import com.example.traveltrip.ui.viewModel.PostViewModel
import com.example.traveltrip.utils.createToast

class DisplayPostFragment : Fragment() {
    private var binding: DisplayPostBinding? = null
    private var viewModel: PostViewModel? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this)[PostViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DisplayPostBinding.inflate(inflater, container, false)

        val postId = arguments?.getString("postID") ?: ""

        observePost()
        observeError()
        binding?.progressBar?.visibility = View.VISIBLE
        viewModel?.getPostByID(postId)

        return binding?.root
    }


    private fun observeError() {
        viewModel?.errorMessage?.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding?.progressBar?.visibility = View.GONE
                createToast(error)
            }

        }
    }


    private fun observePost() {
        viewModel?.post?.observe(viewLifecycleOwner) { post ->
            if (post != null) {
                binding?.progressBar?.visibility = View.GONE
                getPicFromPicasso(binding?.imgPost, post.imgURI)
                binding?.name?.text = post.name
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