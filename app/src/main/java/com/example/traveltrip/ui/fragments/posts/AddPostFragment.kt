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
import com.example.traveltrip.databinding.AddPostBinding
import com.example.traveltrip.model.room.entity.Post
import com.example.traveltrip.model.room.entity.User
import com.example.traveltrip.utils.FieldValidation
import com.example.traveltrip.utils.launchCameraForImage
import com.example.traveltrip.utils.log
import com.example.traveltrip.utils.validateFields
import com.example.traveltrip.ui.viewModel.PostViewModel
import com.example.traveltrip.ui.viewModel.UserViewModel
import com.example.traveltrip.utils.createToast
import com.example.traveltrip.utils.getPicFromPicasso


class AddPostFragment : Fragment() {
    private var binding: AddPostBinding? = null
    private var _bitmap: Bitmap? = null
    private var viewModelPost: PostViewModel? = null
    private var viewModelUser: UserViewModel? = null
    private lateinit var _user: User

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModelPost = ViewModelProvider(this)[PostViewModel::class.java]
        viewModelUser = ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddPostBinding.inflate(inflater, container, false)

        binding?.cancelBtn?.setOnClickListener { findNavController().popBackStack() }
        binding?.saveBtn?.setOnClickListener { handleSave() }
        launchCameraForImage(this, binding?.imgPost, binding?.addPhoto) {
            this._bitmap = it
            binding?.addPhoto?.visibility = View.GONE
            binding?.imgPost?.visibility = View.VISIBLE
        }

        observeSuccessPost()
        observeErrorPost()
        observeErrorUser()
        observeUser()

        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        viewModelUser?.getCurrentUser()
    }

    private fun handleSave() {
        val validation = arrayOf(
//            FieldValidation(binding?.city, "You must provide your city"),
//            FieldValidation(binding?.state, "You must provide your state"),
            FieldValidation(binding?.title, "You must provide title"),
            FieldValidation(binding?.text, "You must provide text")
        )

        if (!validateFields(*validation)) {
            log("please provide me all data")
            return
        }

        binding?.progressBar?.visibility = View.VISIBLE
        val city = ""
//                    binding?.city?.text.toString()
        val state = ""
//                    binding?.state?.text.toString()
        val title = binding?.title?.text.toString()
        val text = binding?.text?.text.toString()
        val userId = this._user.uid

        viewModelPost?.insertPost(
            Post("", city, state, title, text, userId),
            this._bitmap
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
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

    private fun observeErrorUser() {
        viewModelUser?.errorMessage?.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding?.progressBar?.visibility = View.GONE
                createToast(error)
            }
        }
    }

    private fun observeUser() {
        viewModelUser?.user?.observe(viewLifecycleOwner) {
            it?.let { user ->
                this._user = user
                binding?.name?.text = user.name
                getPicFromPicasso(binding?.imgUser, user.img)
            }
        }
    }


    private fun observeErrorPost() {
        viewModelPost?.errorMessage?.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding?.progressBar?.visibility = View.GONE
                createToast(error)
            }
        }
    }
}