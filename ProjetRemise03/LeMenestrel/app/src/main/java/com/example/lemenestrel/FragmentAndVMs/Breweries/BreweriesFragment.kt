package com.example.lemenestrel.FragmentAndVMs.Breweries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        val root: View = binding.root

        val textView: TextView = binding.textBreweries
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