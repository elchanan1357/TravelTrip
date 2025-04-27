package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.traveltrip.databinding.DisplayPostBinding
import com.example.traveltrip.model.ModelPost
import com.example.traveltrip.utils.getPicFromPicasso

class DisplayPostFragment : Fragment() {
    private var binding: DisplayPostBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DisplayPostBinding.inflate(inflater, container, false)
        displayData()

        return binding?.root
    }

    private fun displayData() {
        val postId = arguments?.getString("postID") ?: ""
        ModelPost.instance.getPostByID(postId) { resPost ->
            getPicFromPicasso(binding?.imgPost, resPost?.imgURI)
            binding?.name?.text = resPost?.name
            binding?.city?.text = resPost?.city
            binding?.state?.text = resPost?.state
            binding?.title?.text = resPost?.title
            binding?.text?.text = resPost?.text
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}