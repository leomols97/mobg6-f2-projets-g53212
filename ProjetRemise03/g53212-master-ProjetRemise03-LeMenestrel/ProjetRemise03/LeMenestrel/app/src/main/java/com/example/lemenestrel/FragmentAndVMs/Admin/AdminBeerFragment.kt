package com.example.lemenestrel.FragmentAndVMs.Admin

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lemenestrel.Database.Dao.DataSource
import com.example.lemenestrel.Database.Models.Beers
import com.example.lemenestrel.databinding.FragmentAdminBeerBinding
import com.example.lemenestrel.isOnline
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_admin_beer.*


class AdminBeerFragment : Fragment() {

    companion object {
        const val TAG = "AdminBeerFragment"
        const val SIGN_IN_RESULT_CODE = 1001
    }

    // Get a reference to the ViewModel scoped to this Fragment
    private val viewModel by viewModels<AdminBeerViewModel>()

    val dataSource: DataSource = DataSource()
    val adminBeerViewModel: AdminBeerViewModel = AdminBeerViewModel(dataSource)

    // This is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentAdminBeerBinding? = null
    private lateinit var bind: FragmentAdminBeerBinding
    private val binding get() = _binding!!

    // Have the authenticated user
    private lateinit var auth: FirebaseAuth

    // To be able to work with the database
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminBeerBinding.inflate(inflater, scroll_view_admin_beer, false)
        //_binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_beer, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uploadBeer()
    }

    // Status for the picture upload success
    val IMAGE_BACK = 1
    lateinit var storage: StorageReference
    // To make the picture upload possible
    private fun  uploadBeer() {
        storage = FirebaseStorage.getInstance().reference.child("BeersPictures")
        binding.beerSelectPicture.setOnClickListener {
            selectPicture()
        }
        binding.beerPictureAdmin.setOnClickListener {
            selectPicture()
        }

        binding.deleteBeerAdmin.setOnClickListener {
            if (binding.beerNameAdmin.text.toString() == adminBeerViewModel.getBeerWithName(binding.beerNameAdmin.text.toString())?.Name.toString()) {
                Log.i(TAG, binding.beerNameAdmin.text.toString())
                val mPostReference = FirebaseDatabase.getInstance().getReference()
                    .child("Beers").child(binding.beerNameAdmin.text.toString());
                mPostReference.removeValue();
            } else {
                Toast.makeText(
                    context,
                    "Cette bière n'a pas encore été promue par nos soins \uD83D\uDE22",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun selectPicture() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        Intent(Intent.ACTION_GET_CONTENT).also {
            it.type = "image/*"
        }
        startActivityForResult(intent, IMAGE_BACK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Upload the picture and toasting the success or failure
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // Have the current connected user tu Firebase
                auth = FirebaseAuth.getInstance()
                val uid = auth.currentUser?.uid

                // Get the Firebase database for a specific table : Beers
                databaseReference = FirebaseDatabase.getInstance().getReference("Beers")

                val pictureData = data!!.getData()
                val pictureNameInApp = binding.beerNameAdmin.text.toString()
                val pictureNameInFirebase: StorageReference =
                    storage.child("beer_$pictureNameInApp")
                binding.beerPictureAdmin.setImageURI(pictureData)
                binding.uploadBeerAdmin.setOnClickListener {
                    if (isOnline(requireContext())) {
                        makePictureUpload(pictureNameInFirebase, pictureData)
                        makeBeerInfosUpload(uid)
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            "Vous n'êtes pas connecté à internet \uD83D\uDE22",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun makeBeerInfosUpload(uid: String?) {
        try {
            var beerName: String = binding.beerNameAdmin.text.toString()
            var beerType: String = binding.beerTypeAdmin.text.toString()
            var beerAlcool: Int = binding.beerAlcoolAdmin.text.toString().toInt()
            var beerBreweries: String = binding.beerBreweriesAdmin.text.toString()
            var beerEbc: Int = binding.beerEbcAdmin.text.toString().toInt()
            var beerIbu: Int = binding.beerIbuAdmin.text.toString().toInt()
            var beerPicture: String = binding.beerPictureAdmin.toString()

            // Make an array out of the breweries received
            val beBreweries = beerBreweries.split(",").toTypedArray()
            val breweries: MutableList<String> = beBreweries.toMutableList()
            val beer = Beers(beerName, beerType, beerAlcool, breweries, beerEbc, beerIbu, beerPicture)

            if (uid != null) {
                if (binding.beerNameAdmin.text.toString() != adminBeerViewModel.getBeerWithName(binding.beerNameAdmin.text.toString())?.Name.toString()) {
                    databaseReference.child(beer.Name).setValue(beer).addOnCompleteListener {
                        if (it.isSuccessful) {
                            uploadBeer()
                            Toast.makeText(
                                requireActivity(),
                                "La bière a été ajoutée au serveur \uD83C\uDF7A",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                requireActivity(),
                                "La bière n'a pas été ajoutée au serveur. Vous n'avez probablement pas le droit de le faire \uD83D\uDE22",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        } catch (exception: NumberFormatException) {
            Toast.makeText(
                requireActivity(),
                "Il faut entrer des nombres pour l'alcool, l'EBC et l'IBU \uD83D\uDE09",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun makePictureUpload(
        pictureNameInFirebase: StorageReference,
        pictureData: Uri?) {
        Log.i(TAG, binding.beerNameAdmin.text.toString())
        Log.i(TAG,
            adminBeerViewModel.getBeerWithName(binding.beerNameAdmin.text.toString())?.Name.toString()
        )
        // Verifies that a new picture for the beer has been selected
        if (binding.beerPictureAdmin.toString() == beer_picture_admin.toString()) {
            // Verifies that no field is empty
            if (!TextUtils.isEmpty(binding.beerNameAdmin.text)
                && !TextUtils.isEmpty(binding.beerAlcoolAdmin.text)
                && !TextUtils.isEmpty(binding.beerBreweriesAdmin.text)
                && !TextUtils.isEmpty(binding.beerEbcAdmin.text)
                && !TextUtils.isEmpty(binding.beerIbuAdmin.text)
                && !TextUtils.isEmpty(binding.beerTypeAdmin.text)) {
                // Verifies that no beer of this name exists in the DB
                if (binding.beerNameAdmin.text.toString() != adminBeerViewModel.getBeerWithName(binding.beerNameAdmin.text.toString())?.Name.toString()) {
                    pictureNameInFirebase.putFile(pictureData!!)
                        .addOnFailureListener {
                            Toast.makeText(
                                requireActivity(),
                                "La mise en ligne de la photo ne s'est pas déroulée correctement \uD83D\uDE29",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    Toast.makeText(
                        context,
                        "Cette bière a déjà été ajoutée à l'app \uD83D\uDE09",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Log.i(TAG, "Photo nulle ? " + (binding.beerPictureAdmin.drawable == null))
                Toast.makeText(
                    context,
                    "N'oublie pas de remplir tous les champs \uD83D\uDE09",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Log.i(TAG, "Photo nulle ? " + (binding.beerPictureAdmin.drawable == null))
            Toast.makeText(
                context,
                "A quoi ressemble ta bière ? Sélectionne une image \uD83D\uDE09",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}