package com.example.lemenestrel.FragmentAndVMs.Admin

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
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
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAuthenticationState()

        uploadPicture()

        binding.authButton.setOnClickListener { launchSignInFlow() }
    }

    // Status for the picture upload success
    val IMAGE_BACK = 1
    lateinit var storage: StorageReference
    // To make the picture upload possible
    private fun uploadPicture() {
        storage = FirebaseStorage.getInstance().reference.child("PictureFolder")
        binding.selectPicture.setOnClickListener {
            selectPicture()
        }
    }

    private fun selectPicture() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, IMAGE_BACK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_RESULT_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // User successfully signed in
                Log.i(
                    TAG,"Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.displayName}!"
                )
                Toast.makeText(
                    requireActivity(),
                    "Connexion réussie",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                Log.i(TAG, "Sign in unsuccessful ${response?.error?.errorCode}")
                Toast.makeText(
                    requireActivity(),
                    "Connexion ratée",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        // Upload the picture and toasting the success or failure
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                val pictureData = data!!.getData()
                val pictureNameInApp = binding.pictureName.text
                val pictureNameInFirebase: StorageReference =
                    storage.child("beer_$pictureNameInApp")
                binding.imageView.setImageURI(pictureData)
                binding.uploadPicture.setOnClickListener {
                    if (
                        binding.imageView.drawable != null
//                        &&
//                        !TextUtils.isEmpty(binding.pictureName.text)
                        ) {
                        pictureNameInFirebase.putFile(pictureData!!)
                            .addOnSuccessListener { taskSnapShot ->
                                Toast.makeText(
                                    requireActivity(),
                                    "Photo mise en ligne \uD83C\uDF7A",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }.addOnFailureListener {
                                Toast.makeText(
                                    requireActivity(),
                                    "La mise en ligne de la photo ne s'est pas déroulée correctement \uD83D\uDE29",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    } else {
                        Log.i(TAG, "MLKJHGFDS ")
                        Log.v(TAG, "Photo nulle ? " + (binding.imageView.drawable == null))
                        Toast.makeText(
                            context,
                            "A quoi ressemble ta bière ? Sélectionne une image \uD83D\uDE09 \n" +
                                    "Peut-être as-tu oublié d'inscrire son nom ? \uD83D\uDE09",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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