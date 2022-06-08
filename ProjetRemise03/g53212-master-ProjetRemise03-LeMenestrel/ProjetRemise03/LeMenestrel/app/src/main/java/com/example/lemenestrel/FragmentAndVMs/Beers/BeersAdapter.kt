package com.example.lemenestrel.FragmentAndVMs.Beers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lemenestrel.Database.Models.Beers
import com.example.lemenestrel.R
import kotlinx.android.synthetic.main.item_beer.view.*

class BeersAdapter(val pictureUrls: List<String>, val beers: List<Beers>): RecyclerView.Adapter<BeersAdapter.BeersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeersViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_beer, parent,false)
        return BeersViewHolder(itemView)

    }

    override fun getItemCount(): Int = beers.size

    override fun onBindViewHolder(holder: BeersViewHolder, position: Int) {

        val currentBeer = beers[position]
        var currentPictureUrl = pictureUrls[position]

        // Correcting the beer picture url
        currentPictureUrl = getCorrectPictureURL(currentPictureUrl, currentBeer.Name)

        // Put all the beer data into the right TextViews of the ViewHolder if they're not null
        if (currentBeer.Name == "null") holder.beerName.text = "(Nom manquant)" else holder.beerName.text = currentBeer.Name
        if (currentBeer.Alcool.toString() == "null") holder.beerAlcool.text = "(Alcool manquant)" else holder.beerAlcool.text = currentBeer.Alcool.toString()
        if (currentBeer.Breweries.toString() == "[null]") holder.beerBreweries.text = "(Brasserie manquante)" else holder.beerBreweries.text = currentBeer.Breweries.toString()
        if (currentBeer.EBC.toString() == "null") holder.beerEbc.text = "(Alcool manquant)" else holder.beerEbc.text = currentBeer.EBC.toString()
        if (currentBeer.IBU.toString() == "null") holder.beerIbu.text = "(Alcool manquant)" else holder.beerIbu.text = currentBeer.IBU.toString()
        if (currentBeer.Type == "null") holder.beerType.text = "(Alcool manquant)" else holder.beerType.text = currentBeer.Type

        // Put the picture at a certain url into the ViewHolder ImageView
        Glide.with(holder.itemView.context).load(currentPictureUrl).error(R.drawable.missing).into(holder.itemView.beer_picture_item)
    }

    /**
     * This method parses an URL to replace the beerName it has into with the beerName it has to be
     * @return the new url
     */
    private fun getCorrectPictureURL(url: String, beerName : String): String {
        val parsedUrlBeforeBeerName = url.split("PictureFolder%2F").toMutableList()
        val parsedUrlAfterBeerName = parsedUrlBeforeBeerName[1].split("?").toMutableList()
        var newURL = parsedUrlBeforeBeerName[0] + "PictureFolder%2F"
        parsedUrlBeforeBeerName[1] = "beer_"
        parsedUrlBeforeBeerName[1] += beerName
        newURL += parsedUrlBeforeBeerName[1]
        if (parsedUrlAfterBeerName.size > 2) {
            for (i in 2..parsedUrlAfterBeerName.size) {
                parsedUrlAfterBeerName[1] += parsedUrlAfterBeerName[i]
            }
        }
        newURL += "?" + parsedUrlAfterBeerName[1]
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