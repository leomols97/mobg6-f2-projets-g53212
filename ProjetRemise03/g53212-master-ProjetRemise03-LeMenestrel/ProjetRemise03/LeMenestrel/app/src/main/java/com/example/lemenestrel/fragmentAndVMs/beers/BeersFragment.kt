package com.example.lemenestrel.fragmentAndVMs.beers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lemenestrel.database.models.Beer
import com.example.lemenestrel.databinding.FragmentBeersBinding
import com.example.lemenestrel.databinding.ItemBeerBinding
import com.example.lemenestrel.isOnline
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_beers.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class BeersFragment : Fragment(), BeersAdapter2.BeersInterface {

    companion object {
        const val TAG = "BeersFragment"
        const val SIGN_IN_RESULT_CODE = 1001
    }

    // This is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentBeersBinding? = null
    private val binding get() = _binding!!
    private var _bindingItem: ItemBeerBinding? = null

    // References the Firebase folder with all the beer pictures
    private val picturesReference = FirebaseStorage.getInstance().reference

//    private val beersViewModel: BeersViewModel by lazy {
//        val activity = requireNotNull(this.activity) {
//            "You can only access the viewModel after onActivityCreated()"
//        }
//        ViewModelProvider(this, BeersViewModelFactory(/*activity.application*/))
//            .get(BeersViewModel::class.java)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBeersBinding.inflate(inflater, container, false)
        val root: View = binding.root


        // These are trials to make the ViewBinding, but failed



        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
//        binding.lifecycleOwner = this
//        //Get a reference to the ViewModel associated with this fragment.
//        val viewModelFactory = BeersViewModelFactory()
//        val beersViewModel = ViewModelProvider(this, viewModelFactory).get(BeersViewModel::class.java)v
//
//        val beersAdapter = BeersAdapter(BeersListener { beerName ->
//            Toast.makeText(context, "${beerName}", Toast.LENGTH_LONG).show()
//            beersViewModel.onBeerClicked(beerName)
//        })
//
//        binding.recyclerViewBeers.adapter = beersAdapter
//
//        beersViewModel.beersList.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                beersAdapter.submitList(it)
//            }
//        })

//        beersViewModel.navigateToBeerDetail.observe(viewLifecycleOwner, Observer { beerName ->
//            beerName?.let {
//                this.findNavController().navigate(
//                    BeersFragmentDirections.actionNavBeersToBeerDetailFragment(beerName))
//                beersViewModel.onBeerDetailNavigated()
//            }
//        })
//        val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
//        binding.recyclerViewBeers.layoutManager = manager

//        val binding: FragmentBeersBinding =
//            DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_beers)
//
//        binding.lifecycleOwner = this
//
//        binding.beersViewModel = beersViewModel
//
//        val dao: Dao = Dao()
//        val beers = dao.getBeers()
//        val picturesUrls = mutableListOf<String>()
//        beers?.let {
//            for (beer in it) {
//                picturesUrls.add(beer.Picture)
//            }
//        }
//        val beersAdapter = BeersAdapter2(picturesUrls, WeakReference(this))
//        binding.recyclerViewBeers.adapter = beersAdapter
//
//        beersViewModel.fetchBeersFeed()

        handlingNoInternetConnexion()

        showListBeers()
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
                val beersAdapter = BeersAdapter(picturesUrls, beersData)
                // This is the manner to call the RecyclerView. Declaring this
                // private lateinit var recyclerView: RecyclerView
                // won't let me apply { } on it
                recycler_view_beers.apply {
                    adapter = beersAdapter
                    layoutManager = LinearLayoutManager(requireActivity())
                }
            }
        } catch (e :Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getBeers(): List<Beer> {
        val beers: MutableList<Beer> = mutableListOf()
        val ref = FirebaseDatabase.getInstance().getReference("Beers")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val children = dataSnapshot.children
                    children.forEach {
                        // This returns every brewery in a single String
                        val breweriesString: Array<String> = arrayOf(it.child("breweries").value.toString())
                        val beerName = it.child("name").value.toString()
                        val beerType = it.child("type").value.toString()
                        val beerAlcool = it.child("alcool").value.toString().toDouble()
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

    // region NewsFeedRecyclerViewAdapter.NewsFeedItemInterface
//    override fun onBeerClicked(url: String) {
//        val intent = Intent(this, DetailActivity::class.java).apply {
//            putExtra(DetailActivity.ARG_URL, url)
//        }
//
//        startActivity(intent)
//    }
}