package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveltrip.adapter.GenericAdapter
import com.example.traveltrip.databinding.PostsBinding
import com.example.traveltrip.databinding.RowPostsBinding
import com.example.traveltrip.model.ModelPost
import com.example.traveltrip.model.ModelUser
import com.example.traveltrip.model.entity.Post

class PostsFragment : Fragment() {
    private var binding: PostsBinding? = null
    private var posts: List<Post>? = null
    private var adapter: GenericAdapter<Post, RowPostsBinding>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PostsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.addBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_posts_addPost)
        }

        createAdapter()
        getAllPost()

        val recyclerView: RecyclerView? = binding?.recyclerView
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getAllPost() {
        ModelPost.instance.getAllPosts {
            this.posts = it
            this.adapter?.updateList(this.posts)
        }
    }

    private fun createAdapter() {
        this.adapter = GenericAdapter(
            this.posts,
            RowPostsBinding::inflate
        ) { vb, item ->
            val email = ModelUser.instance.getEmail() ?: ""
            ModelUser.instance.getUserByEmail(email) {
                vb.name.text = it?.name
                vb.title.text = item.title
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}