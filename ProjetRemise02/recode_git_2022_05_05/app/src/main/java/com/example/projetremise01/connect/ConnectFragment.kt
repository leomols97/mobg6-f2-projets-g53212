package com.example.projetremise01.connect

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.projetremise01.R
import com.example.projetremise01.databinding.ConnectFragmentBinding


class ConnectFragment : Fragment() {

    private lateinit var binding: ConnectFragmentBinding

    private lateinit var connectViewModel : ConnectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.connect_fragment, container, false)

        setHasOptionsMenu(true)

        connectViewModel = ViewModelProvider(this).get(ConnectViewModel::class.java)

        binding.connectViewModel = connectViewModel

        binding.lifecycleOwner = viewLifecycleOwner

        binding.connect.setOnClickListener {
            connectButton()
        }

//        val application = requireNotNull(this.activity).application
//
//        if (viewModel.initializeDatabase(context).)
//        viewModel.initializeDatabase(context)


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    private fun connectButton() {
        if (!isValidEmail(binding.mailAdress.text)) {
            val toast = Toast.makeText(
                context,
                "L'adresse mail n'est pas correctement entrée",
                Toast.LENGTH_SHORT
            )
            toast.show()
        }
        else {
            val toast = Toast.makeText(
                context,
                "Adresse mail valide !",
                Toast.LENGTH_SHORT
            )
            toast.show()
            connectViewModel.insertEmail(binding.mailAdress.text.toString())
        }
        // Hides the keybooard
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target.toString()).matches()
    }
}