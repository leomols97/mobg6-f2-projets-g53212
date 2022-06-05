package com.example.lemenestrel.FragmentAndVMs.Beers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lemenestrel.Database.Models.Beers
import com.example.lemenestrel.databinding.FragmentBeersBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class BeersFragment : Fragment() {

    companion object {
        const val TAG = "AdminBeerFragment"
        const val SIGN_IN_RESULT_CODE = 1001
    }

    private var _binding: FragmentBeersBinding? = null

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

        val textView: TextView = binding.textBeers
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}