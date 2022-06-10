package com.example.lemenestrel

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lemenestrel.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMainSendEmail.toolbar)

        handlingNavDrawerMenu()
        handlingEmailButton()
    }

    private fun handlingNavDrawerMenu() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.mainNavView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_who,
                R.id.nav_breweries,
                R.id.nav_events,
                R.id.nav_artists,
                R.id.nav_beers,
                R.id.nav_login
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        // This makes the nav drawer menu 2nd @ 3rd items show instead of having the default icon
        navView.itemIconTintList = null
    }

    private fun handlingEmailButton() {
        val message =
            "A envoyer à leopodl@menestre.be" +
                    "\n\nSujet :" +
                    "\n\nA propos de (un événement, une brasserie, un artiste) :\n" +
                    "\n\nQuels sont tes conseils ? \n" +
                    "\n\nDe la part de :\n"
        binding.appBarMainSendEmail.fab.setOnClickListener { view ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                data = Uri.parse("mailto:")
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, "leopold@menestrel.be")
                putExtra(Intent.EXTRA_SUBJECT, "Suggestion pour le Ménestrel")
                putExtra(Intent.EXTRA_TEXT, message)
            }
            if (intent.resolveActivity(packageManager) != null) {
                intent.setPackage("com.google.android.gm")
                startActivity(intent)
            } else {
                Log.d("TAG", "No app available to send email.")
                Toast.makeText(
                    this,
                    "Aucune app de mail n'est installée",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}