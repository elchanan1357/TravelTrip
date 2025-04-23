package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.databinding.EditPostBinding
import com.example.traveltrip.model.ModelPost
import com.example.traveltrip.utils.getPicFromPicasso
import com.example.traveltrip.utils.isNull
import com.example.traveltrip.utils.log


class EditPostFragment : Fragment() {
    private var binding: EditPostBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EditPostBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayData()
        binding?.saveBtn?.setOnClickListener { handleSave() }
    }


    private fun handleSave() {
        val checking = isNull(binding?.city)
                || isNull(binding?.state)
                || isNull(binding?.title)
                || isNull(binding?.text)

        if (checking) {
            log("please provide me all data in login")
            return
        }



        findNavController().popBackStack()
    }

    private fun displayData() {
        val postId = arguments?.getString("postID") ?: ""
        ModelPost.instance.getPostByID(postId) { resPost ->
            getPicFromPicasso(binding?.imgPost, resPost?.imgURI)
            binding?.city?.setText(resPost?.city)
            binding?.state?.setText(resPost?.state)
            binding?.title?.setText(resPost?.title)
            binding?.text?.setText(resPost?.text)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}