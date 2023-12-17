package com.example.finalapp.ui.signup

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.finalapp.R
import com.example.finalapp.databinding.FragmentLoginBinding
import com.example.finalapp.databinding.FragmentSignupBinding
import com.example.finalapp.models.Signup
import com.example.finalapp.ui.login.LoginViewModel
import com.example.finalapp.ui.utils.Utils
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SignupFragment : Fragment() {

    private lateinit var viewModel: SignupViewModel
    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SignupViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.signupBtn.setOnClickListener {
            val pair = viewModel.isDetailsValidated(viewModel.mSignupLiveData.value ?: Signup())
            if (pair.first) {
                viewModel.performSignupOnFirebase(
                    viewModel.mSignupLiveData.value ?: return@setOnClickListener
                )
                Toast.makeText(
                    requireContext(),
                    viewModel.mSignupLiveData.value.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(requireContext(), pair.second, Toast.LENGTH_SHORT).show()
            }
        }

        binding.profileImv.setOnClickListener {
            showCustomDialog()
        }
        viewModel.resultLiveData.observe(viewLifecycleOwner){
            it?.apply {
                if(it.isSuccess){
                    Toast.makeText(requireActivity(), it.getOrDefault("Success!").toString(), Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }else{
                    Toast.makeText(requireContext(), it.getOrDefault("Failed!").toString(), Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

    // Inside your Activity or Fragment

    // Function to show custom dialog
    private fun showCustomDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.choose_option_dialog)

        // Set corner radius for the dialog
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.attributes?.windowAnimations =
            com.google.android.material.R.style.MaterialAlertDialog_Material3_Animation

        val btnGallery = dialog.findViewById<Button>(R.id.btnGallery)
        val btnCamera = dialog.findViewById<Button>(R.id.btnCamera)

        // Set onClickListeners for the buttons
        btnGallery.setOnClickListener {
            // Handle "Choose From Gallery" button click
            // You can add your logic here
            openGallery()
            dialog.dismiss() // Dismiss the dialog after handling the action
        }

        btnCamera.setOnClickListener {
            // Handle "Camera" button click
            // You can add your logic here
            openCamera()
            dialog.dismiss() // Dismiss the dialog after handling the action
        }

        dialog.show()
    }


    // Inside your Activity or Fragment

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        // Handle the gallery intent result here
        if (uri != null) {
            Log.d("TAG","Gallery Image Uri: $uri")
            viewModel.updateImageUri(uri.toString())
            // Do something with the selected image URI
            // For example, set it to an ImageView or perform further operations
        }
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        // Handle the camera intent result here
        if (bitmap != null) {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File
                null
            }
            val imageUri =  Utils.bitmapToFile(bitmap,requireActivity().application,photoFile?:return@registerForActivityResult)
            Log.d("TAG","Bitmap to file: $imageUri")
            viewModel.updateImageUri(imageUri.toString())
            // Do something with the captured image bitmap
            // For example, set it to an ImageView or perform further operations
        }
    }

    // Function to handle "Choose From Gallery" button click
    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    // Function to handle "Camera" button click
    private fun openCamera() {
        cameraLauncher.launch(null)
    }



    lateinit var currentPhotoPath: String
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)?:throw IOException()
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

}