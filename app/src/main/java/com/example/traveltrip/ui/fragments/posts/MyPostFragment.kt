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
import com.example.traveltrip.utils.getPicFromPicasso
import com.example.traveltrip.ui.viewModel.MyPostViewModel as MyPostViewModel1


class MyPostFragment : Fragment() {
    private var binding: MyPostsBinding? = null
    private var viewModel: MyPostViewModel1? = null
    private var adapter: GenericAdapter<Post, RowMyPostsBinding>? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this)[MyPostViewModel1::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyPostsBinding.inflate(inflater, container, false)
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
    }


    @SuppressLint("SetTextI18n")
    private fun createAdapter() {
        this.adapter = GenericAdapter(
            viewModel?.posts,
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


    private fun getAllPostByEmail() {

//        val email = RoomUser.instance.getEmail() ?: ""
//        RoomPost.instance.getAllPostsByEmail(email) {
//            viewModel?.set(it)
//            adapter?.updateList(viewModel?.posts)
//            binding?.progressBar?.visibility = View.GONE
//        }
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