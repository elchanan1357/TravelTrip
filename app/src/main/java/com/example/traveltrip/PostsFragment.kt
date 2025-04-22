package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.databinding.PostsBinding

class PostsFragment : Fragment() {
    private var binding: PostsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PostsBinding.inflate(inflater, container, false)

        binding?.postsAddBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_posts_addPost)
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}