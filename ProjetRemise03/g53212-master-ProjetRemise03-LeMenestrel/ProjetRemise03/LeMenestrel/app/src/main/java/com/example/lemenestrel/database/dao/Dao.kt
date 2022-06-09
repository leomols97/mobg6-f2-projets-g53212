package com.example.lemenestrel.database.dao

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lemenestrel.database.models.Beer
import com.example.lemenestrel.databinding.FragmentAdminBeerBinding
import com.example.lemenestrel.fragmentAndVMs.admin.AdminBeerFragment
import com.example.lemenestrel.fragmentAndVMs.admin.AdminBeerViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_admin_beer.*

/* Handles operations on beersLiveData and holds details about it. */
class Dao() {

    // Only emojis to brighten up the app :)
    private var emojìWink = "\uD83D\uDE09"
    private var emojìSady = "\uD83D\uDE29"
    private var emojìSad = "\uD83D\uDE22"
    private var emojìBeer = "\uD83C\uDF7A"
    private var emojìSmile = "🙂"

    private val beersList = getBeers()
    private val beersLiveData = MutableLiveData(beersList)

    fun getBeers(): List<Beer> {
        val beers: MutableList<Beer> = mutableListOf()
        val ref = FirebaseDatabase.getInstance().getReference("Beers")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val children = dataSnapshot!!.children
                    children.forEach {
                        // This returns every brewery in a single String
                        val breweriesString: Array<String> = arrayOf(it.child("breweries").value.toString())
                        val beerName = it.child("name").value.toString()
                        val beerType = it.child("type").value.toString()
                        val beerAlcool = Integer.parseInt(it.child("alcool").value.toString())
                        val beerEbc = Integer.parseInt(it.child("ebc").value.toString())
                        val beerIbu = Integer.parseInt(it.child("ibu").value.toString())
                        val beerPicture = it.child("picture").value.toString()

                        val beer = Beer(beerName, beerType, beerAlcool, breweriesString.toMutableList(), beerEbc, beerIbu, beerPicture)

                        beers.add(beer)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }
        })
        return beers
    }
    //
//    val ref = FirebaseDatabase.getInstance().getReference("Beers")
//    ref.addListenerForSingleValueEvent(object: ValueEventListener {
//        override fun onDataChange(dataSnapshot: DataSnapshot) {
//            if (dataSnapshot.exists()) {
//                for (dataSnapshotChildren in dataSnapshot.children) {
//                    val beer = dataSnapshotChildren
//                    Log.i(BeersFragment.TAG, beer.getValue().toString())
//                }
//            }
//        }
//        override fun onCancelled(error: DatabaseError) {
//            throw error.toException()
//        }
//    })
//
//    /* Adds flower to liveData and posts value. */
//    fun addFlower(flower: Flower) {
//        val currentList = flowersLiveData.value
//        if (currentList == null) {
//            flowersLiveData.postValue(listOf(flower))
//        } else {
//            val updatedList = currentList.toMutableList()
//            updatedList.add(0, flower)
//            flowersLiveData.postValue(updatedList)
//        }
//    }
//
//    /* Removes flower from liveData and posts value. */
//    fun removeFlower(flower: Flower) {
//        val currentList = flowersLiveData.value
//        if (currentList != null) {
//            val updatedList = currentList.toMutableList()
//            updatedList.remove(flower)
//            flowersLiveData.postValue(updatedList)
//        }
//    }


    // Returns beer thanks to his name
    fun getBeerWithName(name: String): Beer? {
//        beersLiveData.value?.let { beers ->
//            return beers.firstOrNull{ it.Name == name}
//        }
//        return null
        for (beer in beersList)
            if (name == beer.Name)
                return beer
        return null
    }

    fun makePictureUpload(
        pictureNameInFirebase: StorageReference,
        pictureData: Uri?,
        binding: FragmentAdminBeerBinding,
        beer_picture_admin: CircleImageView,
        adminBeerViewModel: AdminBeerViewModel,
        requireActivity: FragmentActivity,
        context: Context
    ) {
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
                                requireActivity,
                                "La mise en ligne de la photo ne s'est pas déroulée correctement ${emojìSady}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    Toast.makeText(
                        context,
                        "Cette bière a déjà été ajoutée à l'app ${emojìWink}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Log.i(AdminBeerFragment.TAG, "Photo nulle ? " + (binding.beerPictureAdmin.drawable == null))
                Toast.makeText(
                    context,
                    "N'oublie pas de remplir tous les champs ${emojìWink}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Log.i(AdminBeerFragment.TAG, "Photo nulle ? " + (binding.beerPictureAdmin.drawable == null))
            Toast.makeText(
                context,
                "A quoi ressemble ta bière ? Sélectionne une image ${emojìWink}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun getBeersList(): LiveData<List<Beer>> {
        return beersLiveData
    }
    //
//    /* Returns a random flower asset for flowers that are added. */
//    fun getRandomFlowerImageAsset(): Int? {
//        val randomNumber = (initialBeersList.indices).random()
//        return initialBeersList[randomNumber].image
//    }
//
    companion object {
        private var INSTANCE: Dao? = null

        fun getDao(): Dao {
            return synchronized(Dao::class) {
                val newInstance = INSTANCE ?: Dao()
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}