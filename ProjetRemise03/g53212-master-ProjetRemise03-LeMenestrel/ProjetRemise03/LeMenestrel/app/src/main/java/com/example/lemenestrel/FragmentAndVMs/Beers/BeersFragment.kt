package com.example.lemenestrel.FragmentAndVMs.Beers

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lemenestrel.Database.Models.Beers
import com.example.lemenestrel.R
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
import java.io.File
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
    var curFile: Uri? = null
    val pictureReference = FirebaseStorage.getInstance().reference
    //    private lateinit var databaseReference: DatabaseReference
//    private val beersViewModel by viewModels<BeersViewModel> {
//        BeersViewModelFactory(this)
//    }

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
        
        listPictures()


        itemBeerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_breweries, container, false)
        itemBeerBinding.buttooooooon.setOnClickListener {
            val pictureName = itemBeerBinding.beeeeeer.text.toString()
            val storageReference = FirebaseStorage.getInstance().reference.child("PictureFolder/$pictureName")

            val localFile = File.createTempFile("tempImage", "jpg")
            storageReference.getFile(localFile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                itemBeerBinding.pictuuuuuuure.setImageBitmap(bitmap)
            }.addOnFailureListener {
                Toast.makeText(
                    requireActivity(),
                    "Pas réussi à aller chercher l'image de ce nom  \uD83D\uDE03",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


//        val textView: TextView = binding.textBeers
//        galleryViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        binding.buttonLoadBeer.setOnClickListener {
//            val beerName: String = binding.beerNameTextView.text.toString()
//            if (beerName.isNotEmpty()) {
            readData(/*beerName*/)
//            } else {
//                Toast.makeText(
//                    requireActivity(),
//                    "Entre un nom de bière  \uD83D\uDE03",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
        }

//        /* Instantiates headerAdapter and flowersAdapter. Both adapters are added to concatAdapter.
//        which displays the contents sequentially */
//        val headerAdapter = HeaderAdapter()
//        val beersAdapter = BeersAdapter { flower -> adapterOnClick(flower) }
//        val concatAdapter = ConcatAdapter(headerAdapter)
//
//        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
//        recyclerView.adapter = concatAdapter
//
//        beersViewModel.beersLiveData.observe(this, {
//            it?.let {
//                beersAdapter.submitList(it as MutableList<Beers>)
//                headerAdapter.updateBeersCount(it.size)
//            }
//        })
        return root
    }

    private fun listPictures() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val pictures = pictureReference.child("PictureFolder/").listAll().await()
            val picturesUrls = mutableListOf<String>()
            for(picture in pictures.items) {
                val url = picture.downloadUrl.await()
                picturesUrls.add(url.toString())
            }
            withContext(Dispatchers.Main) {
                val imageAdapter = BeersAdapter(picturesUrls)
                recycler_view_beers.apply {
                    adapter = imageAdapter
//                    layoutManager = LinearLayoutManager(this@MainActivity)
                    layoutManager = LinearLayoutManager(requireActivity())
                }
            }
        } catch (e :Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readData(/*beerName: String*/) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Beers")
        databaseReference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val beers: MutableList<Beers> = mutableListOf()
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

                        binding.beerName.text = beer.Name
                        binding.beerAlcool.text = beer.Alcool.toString()
                        binding.beerEbc.text = beer.EBC.toString()
                        binding.beerIbu.text = beer.IBU.toString()
                        binding.beerType.text = beer.Type
                        // Simply to delete '[' and ']' that starts and end the string that should be an array
                        var breweries1 = beer.Breweries.toString()
                        breweries1 = breweries1.filterNot { it == '[' }
                        breweries1 = breweries1.filterNot { it == ']' }
                        binding.beerBreweries.text = breweries1
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }
        })
    }

//    // Opens BeerDetailActivity when a RecyclerView item is clicked
//    private fun adapterOnClick(beer: Beers) {
//        val intent = Intent(this, BeerDetailActivity()::class.java)
//        intent.putExtra(BEER_NAME, beer.Name)
//        val intent = Intent(this, BicycleDetailed::class.java)
//        intent.putExtra(listBicycle)[position]
//        tv_item_name.setText(intent.getStringExtra(EXTRA_NAME))
//        tv_item_detail.setText(intent.getStringExtra(EXTRA_DETAILED))
//        startActivity(intent)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}