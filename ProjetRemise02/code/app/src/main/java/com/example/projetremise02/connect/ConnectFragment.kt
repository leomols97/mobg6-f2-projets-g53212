package com.example.projetremise02.connect

import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat.Token.fromBundle
import android.text.TextUtils
import android.util.Patterns
import android.view.*
import android.widget.Toast
import androidx.core.app.Person.fromBundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.media.AudioAttributesCompat.fromBundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.projetremise01.R
import com.example.projetremise01.databinding.ConnectFragmentBinding
import com.example.projetremise02.database.MailDatabase


class ConnectFragment : Fragment() {

//    private lateinit var binding: ConnectFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//        binding = DataBindingUtil.inflate(
//            inflater, R.layout.connect_fragment, container, false)
//
//        binding.connect.setOnClickListener {
//            connectButton()
//        }
//
//        setHasOptionsMenu(true)
//        return binding.root

        val binding: ConnectFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.connect_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = ConnectFragment.

        // Create an instance of the ViewModel Factory.
        val dataSource = MailDatabase.getInstance(application).mailDatabaseDao
        val viewModelFactory = ConnectViewModelFactory(arguments., dataSource)

        // Get a reference to the ViewModel associated with this fragment.
        val sleepQualityViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(SleepQualityViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.sleepQualityViewModel = sleepQualityViewModel

        // Add an Observer to the state variable for Navigating when a Quality icon is tapped.
        sleepQualityViewModel.navigateToSleepTracker.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                    SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment())
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                sleepQualityViewModel.doneNavigating()
            }
        })

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
        // Hides the keybooard
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target.toString()).matches()
    }
}