package com.example.lemenestrel.FragmentAndVMs.Admin

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lemenestrel.Database.Models.Beers
import com.example.lemenestrel.R
import com.example.lemenestrel.databinding.FragmentAdminArtistBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AdminArtistFragment : Fragment() {

    companion object {
        const val TAG = "AdminArtistFragment"
        const val SIGN_IN_RESULT_CODE = 1001
    }

    // Get a reference to the ViewModel scoped to this Fragment
    private val viewModel by viewModels<AdminArtistViewModel>()
    private lateinit var binding: FragmentAdminArtistBinding

    // Have the authenticated user
    private lateinit var auth: FirebaseAuth

    // To be able to work with the database
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_artist, container, false)


//        // Have the current connected user tu Firebase
//        auth = FirebaseAuth.getInstance()
//        val uid = auth.currentUser?.uid
//
//        // Get the Firebase database for a specific table : Beers
//        databaseReference = FirebaseDatabase.getInstance().getReference("Beers")
//
//        binding.upload.setOnClickListener {
//            val beerName = MutableLiveData<String>().apply {binding.pictureName.text.toString() }
//            val beerType = MutableLiveData<String>().apply { binding.beerType.text.toString() }
//            val beerBreweries = MutableLiveData<Array<String>>().apply { binding.beerBreweries.text.toString() }
//            val beerAlcool = MutableLiveData<Int>().apply { binding.beerAlcool.text.toString().toInt() }
//            val beerEbc = MutableLiveData<Int>().apply { binding.beerEbc.text.toString().toInt() }
//            val beerIbu = MutableLiveData<Int>().apply { binding.beerIbu.text.toString().toInt() }
//
//            // Make an array out of the breweries received
//            val beBreweries = beerBreweries.toString().split(",").toTypedArray()
//            val breweries = MutableLiveData<Array<String>>()
//            val test = StringTokenizer(beerBreweries.toString(), ",").toList()
//
//            //<val beer = Beers(beerName, beerType, test, beerAlcool, beerEbc, beerIbu)
//        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        observeAuthenticationState()

//        uploadPicture()

//        binding.authButton.setOnClickListener { launchSignInFlow() }
    }

//    // Status for the picture upload success
//    val IMAGE_BACK = 1
//    lateinit var storage: StorageReference
//    // To make the picture upload possible
//    private fun uploadPicture() {
//        storage = FirebaseStorage.getInstance().reference.child("BeersPictures")
//        binding.selectPicture.setOnClickListener {
//            selectPicture()
//        }
//    }
//
//    private fun selectPicture() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(intent, IMAGE_BACK)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        // Upload the picture and toasting the success or failure
//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//                val pictureData = data!!.getData()
//                val pictureNameInApp = binding.pictureName.text
//                val pictureNameInFirebase: StorageReference =
//                    storage.child(pictureNameInAp)
//                binding.imageView.setImageURI(pictureData)
//                binding.uploadPicture.setOnClickListener {
//                    makeUpload(pictureNameInFirebase, pictureData)
//                }
//            }
//        }
//    }
//
//    private fun makeUpload(
//        pictureNameInFirebase: StorageReference,
//        pictureData: Uri?
//    ) {
//        if (
//            binding.imageView.drawable != null
//    //                        &&
//    //                        !TextUtils.isEmpty(binding.pictureName.text)
//        ) {
//            pictureNameInFirebase.putFile(pictureData!!)
//                .addOnSuccessListener { taskSnapShot ->
//                    Toast.makeText(
//                        requireActivity(),
//                        "Photo mise en ligne \uD83C\uDF7A",
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                }.addOnFailureListener {
//                    Toast.makeText(
//                        requireActivity(),
//                        "La mise en ligne de la photo ne s'est pas déroulée correctement \uD83D\uDE29",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//        } else {
//            Log.i(TAG, "MLKJHGFDS ")
//            Log.v(TAG, "Photo nulle ? " + (binding.imageView.drawable == null))
//            Toast.makeText(
//                context,
//                "A quoi ressemble ta bière ? Sélectionne une image \uD83D\uDE09 \n" +
//                        "Peut-être as-tu oublié d'inscrire son nom ? \uD83D\uDE09",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }
}