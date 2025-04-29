package com.example.traveltrip

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.databinding.EditPostBinding
import com.example.traveltrip.model.ModelPost
import com.example.traveltrip.model.entity.Post
import com.example.traveltrip.utils.FieldValidation
import com.example.traveltrip.utils.getPicFromPicasso
import com.example.traveltrip.utils.launchCameraForImage
import com.example.traveltrip.utils.log
import com.example.traveltrip.utils.logError
import com.example.traveltrip.utils.validateFields


class EditPostFragment : Fragment() {
    private var binding: EditPostBinding? = null
    private var _bitmap: Bitmap? = null
    private var _post: Post? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EditPostBinding.inflate(inflater, container, false)

        launchCameraForImage(this, binding?.imgPost, binding?.addPhoto) {
            this._bitmap = it
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayData()
        binding?.cancelBtn?.setOnClickListener { findNavController().popBackStack() }
        binding?.saveBtn?.setOnClickListener { handleSave() }
        binding?.deleteBtn?.setOnClickListener{deleteMyPost()}
    }

    private fun deleteMyPost() {
        val id = _post?.id
        if (id != null) {
            ModelPost.instance.deletePost(id) {
                findNavController().popBackStack()
            }
        } else {
            logError("Cannot delete post: post ID is null")
        }
    }



    private fun handleSave() {
        val validation = arrayOf(
            FieldValidation(binding?.city, "Enter your city"),
            FieldValidation(binding?.state, "Enter your state"),
            FieldValidation(binding?.title, "Enter the title"),
            FieldValidation(binding?.text, "Enter the text"),
        )


        if (!validateFields(*validation)) {
            log("please provide me all data in login")
            return
        }

        if (this._post == null) {
            logError("Fail not have post")
            return
        }
        binding?.progressBar?.visibility = View.VISIBLE

        val newPost = this._post?.copy(
            city = binding?.city?.text.toString(),
            state = binding?.state?.text.toString(),
            title = binding?.title?.text.toString(),
            text = binding?.text?.text.toString()
        )

        newPost?.let {
            ModelPost.instance.updatePost(newPost, this._bitmap) {
                binding?.progressBar?.visibility = View.GONE
                findNavController().popBackStack()
            }
        }
    }

    private fun displayData() {
        val postId = arguments?.getString("postID") ?: ""
        ModelPost.instance.getPostByID(postId) { resPost ->
            this._post = resPost
            getPicFromPicasso(binding?.imgPost, resPost?.imgURI)
            binding?.name?.text = resPost?.name
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