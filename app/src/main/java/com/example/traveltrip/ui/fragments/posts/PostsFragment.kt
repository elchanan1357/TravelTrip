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
import com.example.traveltrip.ui.viewModel.PostViewModel
import com.example.traveltrip.ui.viewModel.PostWithUser
import com.example.traveltrip.utils.createToast
import com.example.traveltrip.utils.getPicFromPicasso

class PostsFragment : Fragment() {
    private var binding: PostsBinding? = null
    private var viewModelPost: PostViewModel? = null
    private var adapter: GenericAdapter<PostWithUser, RowPostsBinding>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PostsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModelPost = ViewModelProvider(this)[PostViewModel::class.java]
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
        observeErrorPost()
    }


    private fun observeErrorPost() {
        viewModelPost?.errorMessage?.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding?.progressBar?.visibility = View.GONE
                createToast(error)
            }
        }
    }


    private fun observePosts() {
        viewModelPost?.postsWithUsers?.observe(viewLifecycleOwner) { postsWithUsers ->
            postsWithUsers?.let {
                if (postsWithUsers.isNotEmpty()) {
                    adapter?.updateList(postsWithUsers)
                    binding?.progressBar?.visibility = View.GONE
                } else {
                    binding?.progressBar?.visibility = View.GONE
                    createToast("No post yet...")
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        viewModelPost?.getAllPostsWithUsers()
    }


    private fun createAdapter() {
        this.adapter = GenericAdapter(
            viewModelPost?.postsWithUsers?.value,
            RowPostsBinding::inflate
        ) { vb, item ->
            getPicFromPicasso(vb.imgPost, item.post.imgURI)
            getPicFromPicasso(vb.img, item.user.img)
            vb.name.text = item.user.name
            vb.title.text = item.post.title

            vb.rowPost.setOnClickListener {
                findNavController().navigate(
                    PostsFragmentDirections.actionPostsDisplayPost(item.post.id)
                )
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}

