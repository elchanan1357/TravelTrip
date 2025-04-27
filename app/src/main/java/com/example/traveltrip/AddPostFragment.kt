package com.example.traveltrip


import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.databinding.AddPostBinding
import com.example.traveltrip.model.ModelPost
import com.example.traveltrip.model.ModelUser
import com.example.traveltrip.model.entity.Post
import com.example.traveltrip.utils.FieldValidation
import com.example.traveltrip.utils.log
import com.example.traveltrip.utils.validateFields


class AddPostFragment : Fragment() {
    private var binding: AddPostBinding? = null
    private var cameraLauncher: ActivityResultLauncher<Void?>? = null
    private var _bitmap: Bitmap? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddPostBinding.inflate(inflater, container, false)

        binding?.saveBtn?.setOnClickListener { handleSave() }

        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
                binding?.imgPost?.setImageBitmap(bitmap)
                this._bitmap = bitmap
            }

        binding?.addPhoto?.setOnClickListener {
            cameraLauncher?.launch(null)
//            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            if (cameraIntent.resolveActivity(requireContext().packageManager) != null) {
//                Log.d("Camera", "Camera app is available")
//                cameraLauncher?.launch(null)
//            } else {
//                Log.e("Camera", "No camera app found")
//            }

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


        val email = ModelUser.instance.getEmail() ?: ""
        ModelUser.instance.getUserByEmail(email) {
            if (it != null) {
                val name = it.name

                val city = binding?.city?.text.toString()
                val state = binding?.state?.text.toString()
                val title = binding?.title?.text.toString()
                val text = binding?.text?.text.toString()

                ModelPost.instance.insertPost(
                    Post(email, name, "", city, state, title, text),
                    this._bitmap
                ) {
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}