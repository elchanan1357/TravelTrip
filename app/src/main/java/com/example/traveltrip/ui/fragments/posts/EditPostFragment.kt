package com.example.traveltrip.ui.fragments.posts

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.databinding.EditPostBinding
import com.example.traveltrip.model.room.entity.Post
import com.example.traveltrip.model.room.entity.User
import com.example.traveltrip.ui.viewModel.PostViewModel
import com.example.traveltrip.ui.viewModel.UserViewModel
import com.example.traveltrip.utils.FieldValidation
import com.example.traveltrip.utils.createToast
import com.example.traveltrip.utils.getPicFromPicasso
import com.example.traveltrip.utils.launchCameraForImage
import com.example.traveltrip.utils.log
import com.example.traveltrip.utils.logError
import com.example.traveltrip.utils.validateFields


class EditPostFragment : Fragment() {
    private var binding: EditPostBinding? = null
    private var _bitmap: Bitmap? = null
    private var _post: Post? = null
    private var _user: User? = null
    private var viewModelPost: PostViewModel? = null
    private var viewModelUser: UserViewModel? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModelPost = ViewModelProvider(this)[PostViewModel::class.java]
        viewModelUser = ViewModelProvider(this)[UserViewModel::class.java]
    }

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
        observePost()
        observeErrorPost()
        observeSuccessPost()
        displayData()
        binding?.cancelBtn?.setOnClickListener { findNavController().popBackStack() }
        binding?.saveBtn?.setOnClickListener { handleSave() }
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

        if (newPost != null) viewModelPost?.updatePost(newPost, this._bitmap)
        else binding?.progressBar?.visibility = View.GONE
    }


    private fun displayData() {
        binding?.progressBar?.visibility = View.VISIBLE
        observeUser()
        observeErrorUser()
        viewModelUser?.getCurrentUser()
    }


    private fun observeErrorPost() {
        viewModelPost?.errorMessage?.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding?.progressBar?.visibility = View.GONE
                createToast(error)
            }
        }
    }


    private fun observeSuccessPost() {
        viewModelPost?.isSuccess?.observe(viewLifecycleOwner) { success ->
            success?.let {
                if (success) {
                    binding?.progressBar?.visibility = View.GONE
                    findNavController().popBackStack()
                }
            }
        }
    }


    private fun observePost() {
        viewModelPost?.post?.observe(viewLifecycleOwner) { resPost ->
            if (resPost != null) {
                binding?.progressBar?.visibility = View.GONE
                this._post = resPost
                getPicFromPicasso(binding?.imgPost, resPost.imgURI)
                //TODO fix
                binding?.name?.text = this._user?.name ?: "no user"
                binding?.city?.setText(resPost.city)
                binding?.state?.setText(resPost.state)
                binding?.title?.setText(resPost.title)
                binding?.text?.setText(resPost.text)
            }
        }
    }


    private fun observeErrorUser() {
        viewModelUser?.errorMessage?.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding?.progressBar?.visibility = View.GONE
                createToast(error)
            }
        }
    }


    private fun observeUser() {
        val postId = arguments?.getString("postID") ?: ""
        if (postId.isBlank()) {
            binding?.progressBar?.visibility = View.GONE
            createToast("Not find post")
            //TODO wait
            findNavController().popBackStack()
        }

        viewModelUser?.user?.observe(viewLifecycleOwner) {
            it?.let { user ->
                this._user = user
                viewModelPost?.getPostByID(postId)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}