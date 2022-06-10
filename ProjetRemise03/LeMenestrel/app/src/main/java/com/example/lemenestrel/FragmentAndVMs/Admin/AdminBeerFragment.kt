package com.example.lemenestrel.fragmentAndVMs.admin

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
import com.example.lemenestrel.database.dao.Dao
import com.example.lemenestrel.database.models.Beer
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

        // Status for the picture upload success
        const val IMAGE_BACK = 1
        // Only emojis to brighten up the app :)
        private var emojìWink = "\uD83D\uDE09"
        private var emojìSad = "\uD83D\uDE22"
        private var emojìSady = "\uD83D\uDE29"
        private var emojìBeer = "\uD83C\uDF7A"
        private var emojìSmile = "🙂"
    }

    // Get a reference to the ViewModel scoped to this Fragment
    private val viewModel by viewModels<AdminBeerViewModel>()

    // Link the dao to use it into the ViewModel
    private val dao: Dao = Dao()
    private val adminBeerViewModel: AdminBeerViewModel = AdminBeerViewModel(dao)

    // This is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentAdminBeerBinding? = null
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
        handlingDeleteABeer()
    }

    private fun handlingDeleteABeer() {
        binding.deleteBeerAdmin.setOnClickListener {
            if (isOnline(requireContext())) {
                if (!TextUtils.isEmpty(binding.beerNameAdmin.text)) {
                    if (binding.beerNameAdmin.text.toString() != adminBeerViewModel.getBeerWithName(binding.beerNameAdmin.text.toString())?.Name.toString()) {
                        deleteBeer()
                    } else {
                        Toast.makeText(
                            context,
                            "Cette bière n'a pas encore été promue par nos soins $emojìSad",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Entre d'abord un nom de bière $emojìWink",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Vous n'êtes pas connecté à internet $emojìSad",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun deleteBeer() {
        val beerName = binding.beerNameAdmin.text.toString()
        val beerDataReference = FirebaseDatabase.getInstance().reference
            .child("Beers").child(beerName)
        beerDataReference.removeValue().addOnCompleteListener {
            Toast.makeText(
                context,
                "Données de la bière supprimées avec succès du serveur $emojìSmile",
                Toast.LENGTH_SHORT
            ).show()
        }.addOnFailureListener {
            Toast.makeText(
                context,
                "Données de la bière NON supprimées du serveur. En avez-vous le droit ? Si erreur, envoyez-nous un mail $emojìWink",
                Toast.LENGTH_SHORT
            ).show()
        }
        val beerPictureReference =
            FirebaseStorage.getInstance().reference.child("BeersPictures/")
                .child(beerName)
        beerPictureReference.delete().addOnCompleteListener {
            Toast.makeText(
                context,
                "Photo de la bière supprimée avec succès du serveur $emojìSmile",
                Toast.LENGTH_SHORT
            ).show()
        }.addOnFailureListener {
            Toast.makeText(
                context,
                "Photo de la bière NON supprimée du serveur. En avez-vous le droit ? Si erreur, envoyez-nous un mail $emojìWink",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private lateinit var storage: StorageReference
    // To make the picture upload possible
    private fun  uploadBeer() {
        storage = FirebaseStorage.getInstance().reference.child("BeersPictures")
        binding.beerSelectPicture.setOnClickListener {
            if (!TextUtils.isEmpty(binding.beerNameAdmin.text)) {
                selectPicture()
            } else {
                Toast.makeText(
                    context,
                    "Entre d'abord un nom de bière $emojìWink",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.beerPictureAdmin.setOnClickListener {
            if (!TextUtils.isEmpty(binding.beerNameAdmin.text)) {
                selectPicture()
            } else {
                Toast.makeText(
                    context,
                    "Entre d'abord un nom de bière $emojìWink",
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

                // Get the Firebase database for a specific table : Beer
                databaseReference = FirebaseDatabase.getInstance().getReference("Beers")

                val pictureData = data!!.getData()
                val pictureNameInApp = binding.beerNameAdmin.text.toString()
                val pictureNameInFirebase: StorageReference = storage.child(pictureNameInApp)
                binding.beerPictureAdmin.setImageURI(pictureData)
                binding.uploadBeerAdmin.setOnClickListener {
                    if (isOnline(requireContext())) {
                        makePictureUpload(pictureNameInFirebase, pictureData)
                        makeBeerInfosUpload(uid)
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            "Vous n'êtes pas connecté à internet $emojìSad",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun makeBeerInfosUpload(uid: String?) {
        try {
            val beerName: String = binding.beerNameAdmin.text.toString()
            val beerType: String = binding.beerTypeAdmin.text.toString()
            val beerAlcool: Double = binding.beerAlcoolAdmin.text.toString().toDouble()
            val beerBreweries: String = binding.beerBreweriesAdmin.text.toString()
            val beerEbc: Int = binding.beerEbcAdmin.text.toString().toInt()
            val beerIbu: Int = binding.beerIbuAdmin.text.toString().toInt()
            val beerPicture: String = binding.beerPictureAdmin.toString()

            // Make an array out of the breweries received
            val beBreweries = beerBreweries.split(",").toTypedArray()
            val breweries: MutableList<String> = beBreweries.toMutableList()
            val beer = Beer(beerName, beerType, beerAlcool, breweries, beerEbc, beerIbu, beerPicture)

            if (uid != null) {
                if (binding.beerNameAdmin.text.toString() != adminBeerViewModel.getBeerWithName(binding.beerNameAdmin.text.toString())?.Name.toString()) {
                    databaseReference.child(beer.Name).setValue(beer).addOnCompleteListener {
                        if (it.isSuccessful) {
                            uploadBeer()
                            Toast.makeText(
                                requireActivity(),
                                "La bière a été ajoutée au serveur $emojìBeer",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                requireActivity(),
                                "La bière n'a pas été ajoutée au serveur. En avez-vous le droit ? Si erreur, envoyez-nous un mail $emojìWink",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        } catch (exception: NumberFormatException) {
            Toast.makeText(
                requireActivity(),
                "Il faut entrer des nombres pour l'alcool, l'EBC et l'IBU $emojìWink",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun makePictureUpload(
        pictureNameInFirebase: StorageReference,
        pictureData: Uri?) {
//        dao.makePictureUpload(
//            pictureNameInFirebase,
//            pictureData,
//            binding,
//            beer_picture_admin,
//            adminBeerViewModel,
//            requireActivity(),
//            requireContext())
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
                                "La mise en ligne de la photo ne s'est pas déroulée correctement $emojìSady",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    Toast.makeText(
                        context,
                        "Cette bière a déjà été ajoutée à l'app $emojìWink",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Log.i(TAG, "Photo nulle ? " + (binding.beerPictureAdmin.drawable == null))
                Toast.makeText(
                    context,
                    "N'oublie pas de remplir tous les champs $emojìWink",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Log.i(TAG, "Photo nulle ? " + (binding.beerPictureAdmin.drawable == null))
            Toast.makeText(
                context,
                "A quoi ressemble ta bière ? Sélectionne une image $emojìWink",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}