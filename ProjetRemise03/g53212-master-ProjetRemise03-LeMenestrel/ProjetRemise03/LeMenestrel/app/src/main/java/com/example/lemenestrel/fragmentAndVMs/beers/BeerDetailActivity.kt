package com.example.lemenestrel.fragmentAndVMs.beers

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.lemenestrel.R

class BeerDetailActivity : AppCompatActivity() {

    private val beerDetailViewModel by viewModels<BeerDetailViewModel> {
        BeerDetailViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_beer)

        var currentBeerName: String? = null

        // Connect variables to UI elements
        val beerName: TextView = findViewById(R.id.beer_name_detail)
        val beerType: TextView = findViewById(R.id.beer_type_detail)
        val beerAlcool: TextView = findViewById(R.id.beer_alcool_detail)
        val beerEbc: TextView = findViewById(R.id.beer_ebc_detail)
        val beerIbu: TextView = findViewById(R.id.beer_ibu_detail)
        val beerPicture: ImageView = findViewById(R.id.beer_picture_detail)
        val beerBreweries: TextView = findViewById(R.id.beer_breweries_detail)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentBeerName = bundle.getString(BEER_NAME)
        }

        // If currentBeerName is not null, get corresponding beer information
        currentBeerName?.let {
            val currentBeer = beerDetailViewModel.getBeerWithName(it)
            beerName.text = currentBeer?.Name
            if (currentBeer?.Picture == null) {
                beerPicture.setImageResource(R.drawable.admin)
            } else {
                beerPicture.setImageResource(Integer.parseInt(currentBeer.Picture))
            }
            beerType.text = currentBeer?.Type
            beerAlcool.text = currentBeer?.Alcool.toString()
            beerBreweries.text = currentBeer?.Breweries.toString()
            beerEbc.text = currentBeer?.EBC.toString()
            beerIbu.text = currentBeer?.IBU.toString()
        }
    }
}