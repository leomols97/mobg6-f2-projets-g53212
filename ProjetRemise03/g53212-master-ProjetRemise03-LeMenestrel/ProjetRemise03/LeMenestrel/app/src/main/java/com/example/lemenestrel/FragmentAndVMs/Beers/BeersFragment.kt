package com.example.lemenestrel.FragmentAndVMs.Beers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lemenestrel.Database.Models.Beers
import com.example.lemenestrel.databinding.FragmentBeersBinding
import com.example.lemenestrel.databinding.FragmentBreweriesBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_beers.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.tasks.await
import java.lang.Exception


const val BEER_NAME = "beer name"

class BeersFragment : Fragment() {

    companion object {
        const val TAG = "BeerFragment"
        const val SIGN_IN_RESULT_CODE = 1001
    }

    private var _binding: FragmentBeersBinding? = null
    // Bind the beer item layout
    private lateinit var itemBeerBinding: FragmentBreweriesBinding

    // References the Firebase folder with all the beer pictures
    val picturesReference = FirebaseStorage.getInstance().reference
    val beersReference = FirebaseDatabase.getInstance()

    // List of beers
    private lateinit var beersArray: Array<Beers>

    // For the widgets
    private lateinit var recyclerView: RecyclerView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val beersViewModel =
//            ViewModelProvider(this)[BeersViewModel::class.java]

        _binding = FragmentBeersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Status for the picture upload success
        val IMAGE_BACK = 1
//        imageView2.setOnClickListener {
//            Intent(Intent.ACTION_GET_CONTENT).also {
//                it.type = "image/*"
//                startActivityForResult(it, IMAGE_BACK)
//            }
//        }
        
        listBeers()

//        recyclerView = requireView().findViewById(R.id.recycler_view_beers)

        return root
    }

    private fun listBeers() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val pictures = picturesReference.child("PictureFolder/").listAll().await()
//            beersReference.reference.orderByKey()
            //val beers = beersReference.reference.child("Beers").get().await()
            val beers = getBeers()
            val picturesUrls = mutableListOf<String>()
            val beersData = mutableListOf<Beers>()
            for(picture in pictures.items) {
                val url = picture.downloadUrl.await()
                picturesUrls.add(url.toString())
            }
            for (beer in beers) {
                beersData.add(beer)
            }
            withContext(Dispatchers.Main) {
                val beerAdapter = BeersAdapter(picturesUrls, beersData)
                recycler_view_beers.apply {
                    adapter = beerAdapter
                    layoutManager = LinearLayoutManager(requireActivity())
                }
            }
        } catch (e :Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getBeers(): List<Beers> {
        val beers: MutableList<Beers> = mutableListOf()
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

                        val beer = Beers(beerName, beerType, beerAlcool, breweriesString.toMutableList(), beerEbc, beerIbu, beerPicture)

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