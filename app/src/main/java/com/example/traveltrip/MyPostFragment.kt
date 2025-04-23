package com.example.traveltrip

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveltrip.adapter.GenericAdapter
import com.example.traveltrip.databinding.MyPostsBinding
import com.example.traveltrip.databinding.RowMyPostsBinding
import com.example.traveltrip.model.ModelPost
import com.example.traveltrip.model.ModelUser
import com.example.traveltrip.model.entity.Post
import com.example.traveltrip.utils.getPicFromPicasso
import com.example.traveltrip.utils.log
import com.squareup.picasso.Picasso


class MyPostFragment : Fragment() {
    private var binding: MyPostsBinding? = null
    private var posts: List<Post>? = null
    private var adapter: GenericAdapter<Post, RowMyPostsBinding>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyPostsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createAdapter()
        getAllPostByEmail()

        val recyclerView: RecyclerView? = binding?.recyclerView
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
    }

    @SuppressLint("SetTextI18n")
    private fun createAdapter() {
        this.adapter = GenericAdapter(
            this.posts,
            RowMyPostsBinding::inflate
        ) { vb, item ->
            getPicFromPicasso(vb.img, item.imgURI)
            vb.title.text = item.title
            vb.location.text = "${item.city}, ${item.state}"
            vb.rowMyPost.setOnClickListener {
                log(item.toString())
                val action = MyPostFragmentDirections.actionMyPostsEditPost(item.id)
                findNavController().navigate(action)
            }
        }

    }


    private fun getAllPostByEmail() {
        val email = ModelUser.instance.getEmail() ?: ""
        ModelPost.instance.getAllPostsByEmail(email) {
            this.posts = it
            adapter?.updateList(this.posts)
        }
    }

    override fun onResume() {
        super.onResume()
        getAllPostByEmail()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}