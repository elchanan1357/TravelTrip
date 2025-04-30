package com.example.traveltrip.ui.fragments.posts

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveltrip.R
import com.example.traveltrip.ui.adapter.GenericAdapter
import com.example.traveltrip.databinding.PostsBinding
import com.example.traveltrip.databinding.RowPostsBinding
import com.example.traveltrip.model.room.entity.Post
import com.example.traveltrip.utils.getPicFromPicasso
import com.example.traveltrip.ui.viewModel.PostViewModel

class PostsFragment : Fragment() {
    private var binding: PostsBinding? = null
    private var viewModel: PostViewModel? = null
    private var adapter: GenericAdapter<Post, RowPostsBinding>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PostsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this)[PostViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.addBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_posts_addPost)
        }
        binding?.progressBar?.visibility = View.VISIBLE

        createAdapter()
        val recyclerView: RecyclerView? = binding?.recyclerView
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        observePosts()
    }

    private fun observePosts() {
        viewModel?.posts?.observe(viewLifecycleOwner) { posts ->
            adapter?.updateList(posts)
            binding?.progressBar?.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel?.getAllPost()
    }


    private fun createAdapter() {
        this.adapter = GenericAdapter(
            viewModel?.posts?.value,
            RowPostsBinding::inflate
        ) { vb, item ->
            getPicFromPicasso(vb.imgPost, item.imgURI)
            vb.name.text = item.name
            vb.title.text = item.title

            vb.rowPost.setOnClickListener {
                findNavController().navigate(
                    PostsFragmentDirections.actionPostsDisplayPost(item.id)
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

