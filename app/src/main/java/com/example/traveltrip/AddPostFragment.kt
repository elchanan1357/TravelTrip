package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.databinding.AddPostBinding
import com.example.traveltrip.model.ModelPost
import com.example.traveltrip.model.ModelUser
import com.example.traveltrip.model.entity.Post
import com.example.traveltrip.utils.isNull
import com.example.traveltrip.utils.log


class AddPostFragment : Fragment() {
    private var binding: AddPostBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddPostBinding.inflate(inflater, container, false)

        binding?.saveBtn?.setOnClickListener { handleSave() }

        return binding?.root
    }

    private fun handleSave() {
        val checking = isNull(binding?.city)
                || isNull(binding?.state)
                || isNull(binding?.title)
                || isNull(binding?.text)

        if (checking) {
            log("please provide all data")
            return
        }

        val email = ModelUser.instance.getEmail() ?: ""
        val imgURL = ""
        val city = binding?.city?.text.toString() ?: ""
        val state = binding?.state?.text.toString() ?: ""
        val title = binding?.title?.text.toString() ?: ""
        val text = binding?.text?.text.toString() ?: ""

        ModelPost.instance.insertPost(Post(email, imgURL, city, state, title, text)) {
            findNavController().popBackStack()
        }
    }


}