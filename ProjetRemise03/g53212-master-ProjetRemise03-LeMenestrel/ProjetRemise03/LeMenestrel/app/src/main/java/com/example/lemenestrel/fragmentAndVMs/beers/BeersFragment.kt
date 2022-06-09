package com.example.lemenestrel.fragmentAndVMs.beers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lemenestrel.database.models.Beer
import com.example.lemenestrel.databinding.FragmentBeersBinding
import com.example.lemenestrel.databinding.ItemBeerBinding
import com.example.lemenestrel.isOnline
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.tasks.await
import java.lang.Exception


const val BEER_NAME = "beer name"

class BeersFragment : Fragment() {

    companion object {
        const val TAG = "BeersFragment"
        const val SIGN_IN_RESULT_CODE = 1001
    }

    // This is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentBeersBinding? = null
    private val binding get() = _binding!!
    private var _bindingItem: ItemBeerBinding? = null
    private val bindingItem get() = _bindingItem!!

    // References the Firebase folder with all the beer pictures
    val picturesReference = FirebaseStorage.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBeersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        handlingNoInternetConnexion()
        return root
    }

    private fun handlingNoInternetConnexion() {
        if (isOnline(requireContext())) {
            binding.noInternetConnection.visibility = View.GONE
            binding.reloadButton.visibility = View.GONE
            showListBeers()
        } else {
            binding.noInternetConnection.visibility = View.VISIBLE
            binding.reloadButton.visibility = View.VISIBLE
            binding.reloadButton.setOnClickListener {
                if (isOnline(requireContext())) {
                    binding.noInternetConnection.visibility = View.GONE
                    binding.reloadButton.visibility = View.GONE
                    showListBeers()
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "Vous n'êtes toujours pas connecté à internet \uD83D\uDE09",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showListBeers() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val pictures = picturesReference.child("BeersPictures/").listAll().await()
            val beers = getBeers()
            val picturesUrls = mutableListOf<String>()
            val beersData = mutableListOf<Beer>()
            for (picture in pictures.items) {
                val url = picture.downloadUrl.await()
                picturesUrls.add(url.toString())
            }
            for (beer in beers) {
                beersData.add(beer)
            }
            withContext(Dispatchers.Main) {
//                val beerAdapter = BeersAdapter(picturesUrls, beersData)
//                // This is the manner to call the RecyclerView. Declring this
//                // private lateinit var recyclerView: RecyclerView
//                // won't let me apply { } on it
//                recycler_view_beers.apply {
//                    adapter = beerAdapter
//                    layoutManager = LinearLayoutManager(requireActivity())
//                }
            }
        } catch (e :Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getBeers(): List<Beer> {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}