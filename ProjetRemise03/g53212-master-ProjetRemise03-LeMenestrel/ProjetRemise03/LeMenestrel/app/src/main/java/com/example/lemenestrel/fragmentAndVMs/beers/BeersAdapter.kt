package com.example.lemenestrel.fragmentAndVMs.beers

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lemenestrel.R
import com.example.lemenestrel.database.models.Beer
import com.example.lemenestrel.fragmentAndVMs.beers.BeersAdapter.BeersViewHolder
//import com.example.lemenestrel.fragmentAndVMs.beers.BeersAdapter.BeersViewHolder.Companion.from
import com.example.lemenestrel.databinding.ItemBeerBinding
import kotlinx.android.synthetic.main.fragment_beers.view.*
import kotlinx.android.synthetic.main.item_beer.view.*
import kotlinx.android.synthetic.main.rv_layout.view.*

class BeersAdapter(val pictureUrls: List<String>, val beers: List<Beer>/*, val clickListener: BeersListener*/): RecyclerView.Adapter<BeersViewHolder>() {
    //: ListAdapter<Beer, BeersViewHolder>(BeersDiffCallBack()) {

    companion object {
        const val TAG = "BeersAdapter"
    }

    private lateinit var context: Context

    override fun getItemCount(): Int = beers.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeersViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_beer, parent,false)
        return BeersViewHolder(itemView)
//        return from(parent)
    }

    //    override fun onBindViewHolder(holder: BeersViewHolder, position: Int) {
//        val item = getItem(position)
//        holder.bind(item, clickListener)
//    }
    override fun onBindViewHolder(holder: BeersViewHolder, position: Int) {

        try {
            val currentBeer = beers[position]
            var currentPictureUrl = pictureUrls[position]

            // Correcting the beer picture url
            currentPictureUrl = getCorrectPictureURL(currentPictureUrl, currentBeer.Name)

            // Put all the beer data into the right TextViews of the ViewHolder if they're not null
            if (currentBeer.Name == "null") holder.itemView.beer_name_item.text =
                "(Nom manquant)" else holder.itemView.beer_name_item.text = currentBeer.Name
            if (currentBeer.Alcool.toString() == "null") holder.itemView.beer_alcool_item.text =
                "(Alcool manquant)" else holder.itemView.beer_alcool_item.text = currentBeer.Alcool.toString()
            if (currentBeer.Breweries.toString() == "[null]") holder.itemView.beer_breweries_item.text =
                "(Brasserie manquante)"
            else {
                var breweries = currentBeer.Breweries.toString()
                // This is to remove the "[" and "]" of the breweries string
                breweries = breweries.drop(2)
                breweries = breweries.dropLast(2)
                holder.itemView.beer_breweries_item.text = breweries
            }
            if (currentBeer.EBC.toString() == "null") holder.itemView.beer_ebc_item.text =
                "(EBC manquant)" else holder.itemView.beer_ebc_item.text = currentBeer.EBC.toString()
            if (currentBeer.IBU.toString() == "null") holder.itemView.beer_ibu_item.text =
                "(IBU manquant)" else holder.itemView.beer_ibu_item.text = currentBeer.IBU.toString()
            if (currentBeer.Type == "null") holder.itemView.beer_type_item.text =
                "(Type manquant)" else holder.itemView.beer_type_item.text = currentBeer.Type

            // Put the picture at a certain url into the ViewHolder ImageView
            Glide.with(holder.itemView.context).load(currentPictureUrl).error(R.drawable.missing)
                .into(holder.itemView.beer_picture_item)
        } catch (e: Exception) {
            Toast.makeText(context, "Erreur de différence entre le nombre de bières et le nombre de photos de bières", Toast.LENGTH_SHORT).show()
        }
    }

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

    //    class BeersViewHolder constructor(val binding: ItemBeerBinding): RecyclerView.ViewHolder(binding.root) {
    class BeersViewHolder(itemBeer : View) : RecyclerView.ViewHolder(itemBeer){
        fun bind(beer: Beer, clickListener: BeersListener) {
            // Mets à jour les variables du layout

//            binding.beerNameItem.setText(beer.Name)
//            Log.i(TAG+" Name", beer.Name)
//            binding.beerAlcoolItem.setText(Integer.toString(beer.Alcool))
//            Log.i(TAG+" Alcool", Integer.toString(beer.Alcool))
//            binding.beerTypeItem.text = beer.Type
//            Log.i(TAG+" Type", beer.Type)
//            binding.beerAlcoolItem.setText(Integer.toString(beer.EBC))
//            Log.i(TAG+" EBC", Integer.toString(beer.EBC))
//            binding.beerAlcoolItem.setText(Integer.toString(beer.IBU))
//            Log.i(TAG+" IBU", Integer.toString(beer.IBU))
//            binding.beerAlcoolItem.setText(beer.Breweries.joinToString())
//            Log.i(TAG+" Breweries", beer.Breweries.joinToString())
//            Log.i(TAG+" Picture", beer.Picture)
//            binding.beerBreweriesItem.text = beer.Breweries.toString()
//            Glide.with(itemView).load(beer.Picture).error(R.drawable.missing)


//            binding.beer = beer
//            Log.i(TAG, beer.Name)
//            binding.clickListener = clickListener
//            Glide.with(holder.itemView.context).load(currentPictureUrl).error(R.drawable.missing)

//            binding.executePendingBindings()
        }
//        companion object {
//            fun from(parent: ViewGroup): BeersViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val binding =
//                    ItemBeerBinding.inflate(layoutInflater, parent, false)
//                return BeersViewHolder(binding)
//            }
//        }
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