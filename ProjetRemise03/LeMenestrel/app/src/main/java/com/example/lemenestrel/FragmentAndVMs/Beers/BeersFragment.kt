package com.example.lemenestrel.FragmentAndVMs.Beers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lemenestrel.databinding.FragmentBeersBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class BeersFragment : Fragment() {

    private var _binding: FragmentBeersBinding? = null
    private lateinit var databaseReference: DatabaseReference

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this)[BeersViewModel::class.java]

        _binding = FragmentBeersBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textBeers
//        galleryViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        binding.buttonLoadBeer.setOnClickListener {
            val beerName: String = binding.beerNameTextView.text.toString()
            if (beerName.isNotEmpty()) {
                readData(beerName)
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Entre un nom de bière  \uD83D\uDE03",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return root
    }

    private fun readData(beerName: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Beers")
        databaseReference.child(beerName).get().addOnSuccessListener {
            if (it.exists()) {
                val beerName = it.child("beerName").value
                val beerAlcool = it.child("alcool").value
                val beerEbc = it.child("ebc").value
                val beerIBU = it.child("ibu").value
                val beerType = it.child("type").value
                val beerBreweries = it.child("breweries").value
//                val breweries: String
//                for (i: Int; i < beerBreweries.toString().length; i++)

                Toast.makeText(
                    requireActivity(),
                    "Voici les informations à propos de la bière que vous avez demandée",
                    Toast.LENGTH_SHORT
                ).show()

                binding.beerName.text = beerName.toString()
                binding.beerAlcool.text = beerAlcool.toString()
                binding.beerEbc.text = beerEbc.toString()
                binding.beerIbu.text = beerIBU.toString()
                binding.beerType.text = beerType.toString()
                binding.beerBreweries.text = beerBreweries.toString()

            } else {
                Toast.makeText(
                    requireActivity(),
                    "Cette bière n'a pas encore été promue par nos soins \uD83D\uDE22",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.addOnFailureListener {
            Toast.makeText(
                requireActivity(),
                "Une erreur s'est produite \uD83D\uDE22",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}