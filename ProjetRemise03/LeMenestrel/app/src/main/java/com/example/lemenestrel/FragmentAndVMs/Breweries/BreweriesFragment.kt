package com.example.lemenestrel.fragmentAndVMs.breweries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lemenestrel.databinding.FragmentBreweriesBinding

class BreweriesFragment : Fragment() {

    private var _binding: FragmentBreweriesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this)[BreweriesViewModel::class.java]

        _binding = FragmentBreweriesBinding.inflate(inflater, container, false)

//        val textView: TextView = binding.textBrewery
//        galleryViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}