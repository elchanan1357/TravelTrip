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
import com.example.traveltrip.utils.getPicFromPicasso
import com.example.traveltrip.utils.isNull
import com.example.traveltrip.utils.log
import com.example.traveltrip.utils.logError


class EditPostFragment : Fragment() {
    private var binding: EditPostBinding? = null
    private var cameraLauncher: ActivityResultLauncher<Void?>? = null
    private var _bitmap: Bitmap? = null
    private var _post: Post? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EditPostBinding.inflate(inflater, container, false)

        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
                binding?.imgPost?.setImageBitmap(bitmap)
                this._bitmap = bitmap
            }

        binding?.addPhoto?.setOnClickListener {
            cameraLauncher?.launch(null)
        }

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

        if (this._post == null) {
            logError("Fail not have post")
            return
        }

        val newPost = this._post?.copy(
            city = binding?.city?.text.toString(),
            state = binding?.state?.text.toString(),
            title = binding?.title?.text.toString(),
            text = binding?.text?.text.toString()
        )

        ModelPost.instance.updatePost(newPost!!.id, newPost, this._bitmap) {
            findNavController().popBackStack()
        }

    }

    private fun displayData() {
        val postId = arguments?.getString("postID") ?: ""
        ModelPost.instance.getPostByID(postId) { resPost ->
            this._post = resPost
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