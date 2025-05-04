package com.example.traveltrip.ui.fragments.posts

import android.annotation.SuppressLint
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
import com.example.traveltrip.ui.adapter.GenericAdapter
import com.example.traveltrip.databinding.MyPostsBinding
import com.example.traveltrip.databinding.RowMyPostsBinding
import com.example.traveltrip.model.room.entity.Post
import com.example.traveltrip.ui.viewModel.PostViewModel
import com.example.traveltrip.utils.createToast
import com.example.traveltrip.utils.getPicFromPicasso


class MyPostFragment : Fragment() {
    private var binding: MyPostsBinding? = null
    private var adapter: GenericAdapter<Post, RowMyPostsBinding>? = null
    private var viewModel: PostViewModel? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this)[PostViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyPostsBinding.inflate(inflater, container, false)

        binding?.swipeRefresh?.setOnRefreshListener {
            viewModel?.getPostsByUserID()
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.progressBar?.visibility = View.VISIBLE
        createAdapter()
        val recyclerView: RecyclerView? = binding?.recyclerView
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        observePosts()
        observeError()
    }


    private fun observeError() {
        viewModel?.errorMessage?.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding?.swipeRefresh?.isRefreshing = false
                binding?.progressBar?.visibility = View.GONE
                createToast(error)
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun createAdapter() {
        this.adapter = GenericAdapter(
            viewModel?.posts?.value,
            RowMyPostsBinding::inflate
        ) { vb, item ->
            getPicFromPicasso(vb.img, item.imgURI)
            vb.title.text = item.title
            vb.location.text = "${item.city}, ${item.state}"
            vb.rowMyPost.setOnClickListener {
                findNavController().navigate(
                    MyPostFragmentDirections.actionMyPostsEditPost(item.id)
                )
            }
        }

    }


    override fun onResume() {
        super.onResume()
        viewModel?.getPostsByUserID()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun observePosts() {
        viewModel?.posts?.observe(viewLifecycleOwner) { posts ->
            posts?.let {
                if (posts.isNotEmpty()) {
                    adapter?.updateList(posts)
                    binding?.swipeRefresh?.isRefreshing = false
                    binding?.progressBar?.visibility = View.GONE
                }else{
                    binding?.swipeRefresh?.isRefreshing = false
                    binding?.progressBar?.visibility = View.GONE
                    createToast("No post yet...")
                }
            }
        }
    }
}