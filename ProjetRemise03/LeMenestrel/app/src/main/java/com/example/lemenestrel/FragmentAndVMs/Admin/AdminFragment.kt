package com.example.lemenestrel.FragmentAndVMs.Admin

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.lemenestrel.R
import com.example.lemenestrel.databinding.FragmentAdminBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.net.URI

class AdminFragment : Fragment() {

    companion object {
        const val TAG = "AdminFragment"
        const val SIGN_IN_RESULT_CODE = 1001
    }

    // Get a reference to the ViewModel scoped to this Fragment
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentAdminBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin, container, false)
//        uploadPictureProcess()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAuthenticationState()
        uploadPicture()

        binding.authButton.setOnClickListener { launchSignInFlow() }
    }

    // To make the picture upload possible
    val IMAGE_BACK = 1
    lateinit var storage: StorageReference
    private fun uploadPicture() {
        storage = FirebaseStorage.getInstance().reference.child("PictureFolder")
        binding.uploadPicture.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent, IMAGE_BACK)
        }
    }

//    lateinit var pictureURI: Uri
//    private fun uploadPictureProcess() {
//        binding.selectPicture.setOnClickListener{
////            selectPicture()
//            openGalleryForImage()
//        }
//        binding.uploadPicture.setOnClickListener{
//            uploadPicture()
//        }
//    }

//    private fun selectPicture() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(intent, 1000)
//    }

//    private fun openGalleryForImage() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, 100)
//    }

//    private fun uploadPicture() {
//        val progressDialog = ProgressDialog(activity)
//        progressDialog.setMessage("Uploading picture...")
//        progressDialog.setCancelable(true)
//        progressDialog.show()
//
//        val fileName = "testUploading"
//        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")
//
//        storageReference.putFile(pictureURI).addOnSuccessListener {
//            binding.imageView.setImageURI(null)
//            Toast.makeText(requireActivity(), "Picture successfully uploaded !", Toast.LENGTH_SHORT).show()
//            if (progressDialog.isShowing)
//                progressDialog.dismiss()
//        }.addOnFailureListener {
//            if (progressDialog.isShowing)
//                progressDialog.dismiss()
//            Toast.makeText(requireActivity(), "Picture NOT uploaded !", Toast.LENGTH_SHORT).show()
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_RESULT_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // User successfully signed in
                Log.i(
                    TAG,"Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.displayName}!"
                )
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                Log.i(TAG, "Sign in unsuccessful ${response?.error?.errorCode}")
            }
//            if (requestCode == 100 && resultCode == RESULT_OK ) {
//                pictureURI = data?.data!!
//                binding.imageView.setImageURI(pictureURI) // handle chosen image
//            }
        }
        // To make the picture upload possible
        if (requestCode == IMAGE_BACK) {
            if (resultCode == RESULT_OK) {
                val pictureData = data!!.getData()
                val pictureName: StorageReference = storage.child("image" + pictureData!!.lastPathSegment)
                pictureName.putFile(pictureData).addOnSuccessListener { taskSnapShot ->
                    Toast.makeText(requireActivity(), "Picture uploaded", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(requireActivity(), "Picture NOT uploaded", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Observes the authentication state and changes the UI accordingly.
     * If there is a logged in user: (1) show a logout button and (2) display their name.
     * If there is no logged in user: show a login button
     */
    private fun observeAuthenticationState() {
        val factToDisplay = viewModel.getFactToDisplay(requireContext())

        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    binding.welcomeText.text = getFactWithPersonalization(factToDisplay)

                    // Logout don't need any internet connection
                    binding.authButton.text = getString(R.string.logout_button_text)
                    binding.authButton.setOnClickListener {
                        AuthUI.getInstance().signOut(requireContext())
                    }
                }
                else -> {
                    binding.welcomeText.text = factToDisplay

                    binding.authButton.text = getString(R.string.login_button_text)
                    binding.authButton.setOnClickListener {
                        launchSignInFlow()
                    }
                }
            }
        })
    }


    private fun getFactWithPersonalization(fact: String): String {
        return String.format(
            resources.getString(
                R.string.welcome_message_authed,
                FirebaseAuth.getInstance().currentUser?.displayName,
                Character.toLowerCase(fact[0]) + fact.substring(1)
            )
        )
    }

    private fun launchSignInFlow() {
        // Give users the option to sign in / register with their email
        // If users choose to register with their email,
        // they will need to create a password as well
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
            //
        )

        // Create and launch sign-in intent.
        // We listen to the response of this activity with the
        // SIGN_IN_RESULT_CODE code
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(
                providers
            ).build(), SIGN_IN_RESULT_CODE
        )
    }
}