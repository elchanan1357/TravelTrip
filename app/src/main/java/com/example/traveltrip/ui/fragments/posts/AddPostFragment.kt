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
import com.example.traveltrip.utils.FieldValidation
import com.example.traveltrip.utils.launchCameraForImage
import com.example.traveltrip.utils.log
import com.example.traveltrip.utils.validateFields
import com.example.traveltrip.ui.viewModel.PostViewModel


class AddPostFragment : Fragment() {
    private var binding: AddPostBinding? = null
    private var _bitmap: Bitmap? = null
    private var viewModel: PostViewModel? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this)[PostViewModel::class.java]
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
        }

        return binding?.root
    }

    private fun handleSave() {
        val validation = arrayOf(
            FieldValidation(binding?.city, "You must provide your city"),
            FieldValidation(binding?.state, "You must provide your state"),
            FieldValidation(binding?.title, "You must provide title"),
            FieldValidation(binding?.text, "You must provide text")
        )

        if (!validateFields(*validation)) {
            log("please provide me all data")
            return
        }

        binding?.progressBar?.visibility = View.VISIBLE

//        val email = ModelUser.instance.getEmail() ?: ""
//        ModelUser.instance.getUserByEmail(email) {
//            if (it != null) {
//                val name = it.name
//
//                val city = binding?.city?.text.toString()
//                val state = binding?.state?.text.toString()
//                val title = binding?.title?.text.toString()
//                val text = binding?.text?.text.toString()
//
//                ModelPost.instance.insertPost(
//                    Post(email, name, "", city, state, title, text),
//                    this._bitmap
//                ) {
//                    binding?.progressBar?.visibility = View.GONE
//                    findNavController().popBackStack()
//                }
//            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}