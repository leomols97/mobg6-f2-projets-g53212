package com.example.lemenestrel.FragmentAndVMs.Beers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lemenestrel.databinding.FragmentBeersBinding

class BeersFragment : Fragment() {

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