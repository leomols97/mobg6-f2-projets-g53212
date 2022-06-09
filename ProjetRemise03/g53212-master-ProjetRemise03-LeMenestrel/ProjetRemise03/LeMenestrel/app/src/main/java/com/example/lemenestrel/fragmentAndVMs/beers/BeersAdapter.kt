package com.example.lemenestrel.fragmentAndVMs.beers

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lemenestrel.database.models.Beer
import com.example.lemenestrel.fragmentAndVMs.beers.BeersAdapter.BeersViewHolder
import com.example.lemenestrel.fragmentAndVMs.beers.BeersAdapter.BeersViewHolder.Companion.from
import com.example.lemenestrel.databinding.ItemBeerBinding

class BeersAdapter(val clickListener: BeersListener): ListAdapter<Beer, BeersViewHolder>(BeersDiffCallBack()) {
    //: RecyclerView.Adapter<BeersAdapter.BeersViewHolder>() {

    companion object {
        const val TAG = "BeerAdapter"
    }

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeersViewHolder {
        return from(parent)
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeersViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_beer, parent,false)
//        return BeersViewHolder(itemView)
//    }

//    override fun getItemCount(): Int = beers.size

    override fun onBindViewHolder(holder: BeersViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(getItem(position)!!, clickListener)
    }
//    override fun onBindViewHolder(holder: BeersViewHolder, position: Int) {
//
//        try {
//            val currentBeer = beers[position]
//            var currentPictureUrl = pictureUrls[position]
//
//            // Correcting the beer picture url
//            currentPictureUrl = getCorrectPictureURL(currentPictureUrl, currentBeer.Name)
//
//            // Put all the beer data into the right TextViews of the ViewHolder if they're not null
//            if (currentBeer.Name == "null") holder.beerName.text =
//                "(Nom manquant)" else holder.beerName.text = currentBeer.Name
//            if (currentBeer.Alcool.toString() == "null") holder.beerAlcool.text =
//                "(Alcool manquant)" else holder.beerAlcool.text = currentBeer.Alcool.toString()
//            if (currentBeer.Breweries.toString() == "[null]") holder.beerBreweries.text =
//                "(Brasserie manquante)"
//            else {
//                var breweries = currentBeer.Breweries.toString()
//                // This is to remove the firsts 2 characters that are '['
//                // and the last two characters that are ']' of the breweries string
//                breweries = breweries.drop(2)
//                breweries = breweries.dropLast(2)
//                holder.beerBreweries.text = breweries
//            }
//            if (currentBeer.EBC.toString() == "null") holder.beerEbc.text =
//                "(EBC manquant)" else holder.beerEbc.text = currentBeer.EBC.toString()
//            if (currentBeer.IBU.toString() == "null") holder.beerIbu.text =
//                "(IBU manquant)" else holder.beerIbu.text = currentBeer.IBU.toString()
//            if (currentBeer.Type == "null") holder.beerType.text =
//                "(Type manquant)" else holder.beerType.text = currentBeer.Type
//
//            // Put the picture at a certain url into the ViewHolder ImageView
//            Glide.with(holder.itemView.context).load(currentPictureUrl).error(R.drawable.missing)
//                .into(holder.itemView.beer_picture_item)
//        } catch (e: Exception) {
//            Toast.makeText(this.context, "Erreur de différence entre le nombre de bières et le nombre de photos de bières", Toast.LENGTH_SHORT).show()
//        }
//    }

    /**
     * This method parses an URL to replace the beerName it has into with the beerName it has to be
     * @return the new url
     */
    private fun getCorrectPictureURL(url: String, beerName : String): String {
        val parsedUrlBeforeBeerName = url.split("BeersPictures%2F").toMutableList()
        val parsedUrlAfterBeerName = parsedUrlBeforeBeerName[1].split("?").toMutableList()
        var newURL = parsedUrlBeforeBeerName[0] + "BeersPictures%2F"
        newURL += beerName + "?" + parsedUrlAfterBeerName[1]
        return newURL
    }

    class BeersViewHolder constructor(val binding: ItemBeerBinding): RecyclerView.ViewHolder(binding.root) {

//        val beerName: TextView = binding.findViewById(R.id.beer_name_item)
//        val beerType: TextView = binding.findViewById(R.id.beer_type_item)
//        val beerAlcool: TextView = binding.findViewById(R.id.beer_alcool_item)
//        val beerBreweries: TextView = binding.findViewById(R.id.beer_breweries_item)
//        val beerEbc: TextView = binding.findViewById(R.id.beer_ebc_item)
//        val beerIbu: TextView = binding.findViewById(R.id.beer_ibu_item)

        fun bind(beer: Beer, clickListener: BeersListener) {
            // Mets à jour les variables du layout
//            binding.beerNameItem.text = beer.Name
//            binding.beerAlcoolItem.text = beer.Alcool.toString()
//            binding.beerTypeItem.text = beer.Type
//            binding.beerEbcItem.text = beer.EBC.toString()
//            binding.beerIbuItem.text = beer.IBU.toString()
//            binding.beerBreweriesItem.text = beer.Breweries.toString()
//            Glide.with(holder.itemView.context).load(currentPictureUrl).error(R.drawable.missing)

            binding.beer = beer
//            Log.i(TAG, binding.beer.Name)
            Log.i(TAG, beer.Name)
            binding.clickListener = clickListener

            binding.executePendingBindings()
        }

//        fun from() {
//            // Inflate le ItemBeerBinding
//            // retourner une instance de beerviewholder
//        }

        companion object {
            fun from(parent: ViewGroup): BeersViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemBeerBinding.inflate(layoutInflater, parent, false)
                return BeersViewHolder(binding)
            }
        }
    }
}

// Should handle the click on an item in RecyclerView. It is not already the case
class BeersListener(val clickListener: (beerName: String) -> Unit) {
    fun onclick(beer: Beer) = clickListener(beer.Name)
}

class BeersDiffCallBack: DiffUtil.ItemCallback<Beer>() {
    override fun areItemsTheSame(oldBeer: Beer, newBeer: Beer): Boolean {
        return oldBeer.Name == newBeer.Name
    }
    override fun areContentsTheSame(oldBeer: Beer, newBeer: Beer): Boolean {
        return oldBeer == newBeer
    }
}