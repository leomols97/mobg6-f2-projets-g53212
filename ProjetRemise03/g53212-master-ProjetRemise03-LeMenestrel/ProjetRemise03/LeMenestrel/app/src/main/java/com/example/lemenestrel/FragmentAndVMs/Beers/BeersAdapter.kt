package com.example.lemenestrel.FragmentAndVMs.Beers

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lemenestrel.Database.Models.Beer
import com.example.lemenestrel.R
import kotlinx.android.synthetic.main.item_beer.view.*

class BeersAdapter(val pictureUrls: List<String>, val beers: List<Beer>): RecyclerView.Adapter<BeersAdapter.BeersViewHolder>() {

    companion object {
        const val TAG = "BeerAdapter"
    }

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeersViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_beer, parent,false)
        return BeersViewHolder(itemView)

    }

    override fun getItemCount(): Int = beers.size

    override fun onBindViewHolder(holder: BeersViewHolder, position: Int) {

        try {
            val currentBeer = beers[position]
            var currentPictureUrl = pictureUrls[position]

            // Correcting the beer picture url
            currentPictureUrl = getCorrectPictureURL(currentPictureUrl, currentBeer.Name)

            // Put all the beer data into the right TextViews of the ViewHolder if they're not null
            if (currentBeer.Name == "null") holder.beerName.text =
                "(Nom manquant)" else holder.beerName.text = currentBeer.Name
            if (currentBeer.Alcool.toString() == "null") holder.beerAlcool.text =
                "(Alcool manquant)" else holder.beerAlcool.text = currentBeer.Alcool.toString()
            if (currentBeer.Breweries.toString() == "[null]") holder.beerBreweries.text =
                "(Brasserie manquante)"
            else {
                var breweries = currentBeer.Breweries.toString()
                // This is to remove the firsts 2 characters that are '['
                // and the last two ccharacters that are ']' of the breweries string
                breweries = breweries.drop(2)
                breweries = breweries.dropLast(2)
                holder.beerBreweries.text = breweries
            }
            if (currentBeer.EBC.toString() == "null") holder.beerEbc.text =
                "(EBC manquant)" else holder.beerEbc.text = currentBeer.EBC.toString()
            if (currentBeer.IBU.toString() == "null") holder.beerIbu.text =
                "(IBU manquant)" else holder.beerIbu.text = currentBeer.IBU.toString()
            if (currentBeer.Type == "null") holder.beerType.text =
                "(Type manquant)" else holder.beerType.text = currentBeer.Type

            // Put the picture at a certain url into the ViewHolder ImageView
            Glide.with(holder.itemView.context).load(currentPictureUrl).error(R.drawable.missing)
                .into(holder.itemView.beer_picture_item)
        } catch (e: Exception) {
            Toast.makeText(this.context, "Erreur de différence entre le nombre de bières et le nombre de photos de bières", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * This method parses an URL to replace the beerName it has into with the beerName it has to be
     * @return the new url
     */
    private fun getCorrectPictureURL(url: String, beerName : String): String {
        Log.i(TAG+"1", url)
        Log.i(TAG+"1", beerName)
        val parsedUrlBeforeBeerName = url.split("BeersPictures%2F").toMutableList()
        val parsedUrlAfterBeerName = parsedUrlBeforeBeerName[1].split("?").toMutableList()
        var newURL = parsedUrlBeforeBeerName[0] + "BeersPictures%2F"
        newURL += beerName + "?" + parsedUrlAfterBeerName[1]
        return newURL
    }

    class BeersViewHolder(itemBeer : View) : RecyclerView.ViewHolder(itemBeer){

        val beerName : TextView = itemBeer.findViewById(R.id.beer_name_item)
        val beerType : TextView = itemBeer.findViewById(R.id.beer_type_item)
        val beerAlcool : TextView = itemBeer.findViewById(R.id.beer_alcool_item)
        val beerBreweries : TextView = itemBeer.findViewById(R.id.beer_breweries_item)
        val beerEbc : TextView = itemBeer.findViewById(R.id.beer_ebc_item)
        val beerIbu : TextView = itemBeer.findViewById(R.id.beer_ibu_item)
    }
}