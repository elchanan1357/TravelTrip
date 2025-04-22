package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.traveltrip.databinding.MyPostsBinding


class MyPostFragment : Fragment() {
    private var binding: MyPostsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyPostsBinding.inflate(inflater, container, false)
        return binding?.root
    }
}