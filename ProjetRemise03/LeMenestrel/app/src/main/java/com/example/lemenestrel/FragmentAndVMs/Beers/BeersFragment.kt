package com.example.lemenestrel.FragmentAndVMs.Beers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lemenestrel.Database.Models.Beers
import com.example.lemenestrel.databinding.FragmentBeersBinding
import com.example.lemenestrel.databinding.ItemBeerBinding
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

const val BEER_NAME = "beer name"

class BeersFragment : Fragment() {

    companion object {
        const val TAG = "BeerFragment"
        const val SIGN_IN_RESULT_CODE = 1001
    }

    // Create an instance of the ViewModel Factory.
//    val application = requireNotNull(this.activity).application
//    val databaseReference = FirebaseDatabase.getInstance().getReference("Beers")
//    val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
//    val viewModelFactory = BeersViewModelFactory(databaseReference, application)

    private var _binding: FragmentBeersBinding? = null
    //    private lateinit var databaseReference: DatabaseReference
//    private val beersViewModel by viewModels<BeersViewModel> {
//        BeersViewModelFactory(application)
//    }

    // Get a reference to the ViewModel associated with this fragment.
//    val sleepTrackerViewModel =
//        ViewModelProvider(
//            this, viewModelFactory).get(SleepTrackerViewModel::class.java)

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var itemBeerBinding: ItemBeerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val beersViewModel =
            ViewModelProvider(this)[BeersViewModel::class.java]

        _binding = FragmentBeersBinding.inflate(inflater, container, false)
        val root: View = binding.root

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

        itemBeerBinding.buttonLoadBeer.setOnClickListener {
            val beerPicture =  itemBeerBinding.beerPictureItem.toString()
            val storageReference = FirebaseStorage.getInstance().reference.child("PictureFolder/beer_EFZGB")
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