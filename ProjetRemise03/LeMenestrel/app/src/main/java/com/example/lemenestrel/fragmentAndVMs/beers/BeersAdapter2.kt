package com.example.lemenestrel.fragmentAndVMs.beers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lemenestrel.R
import com.example.lemenestrel.database.models.Beer
import com.example.lemenestrel.databinding.ItemBeerBinding
import kotlinx.android.synthetic.main.item_beer.view.*
import java.lang.ref.WeakReference


// IGNORE THIS CLASS !!!!!



class BeersAdapter2(
    private val pictureUrls: List<String>,
    private val callbackWeakRef: WeakReference<BeersInterface>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface BeersInterface {
//        fun onBeerClicked(url: String)
//        fun onFavoriteStatusChanged(newsFeedItemId: String, newStatus: Boolean)
    }

    private val beers = mutableListOf<Beer>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BeersViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BeersViewHolder).onBind(
            beer = beers[position]
        )
        val currentPictureUrl = pictureUrls[position]

        Glide.with(holder.itemView.context).load(currentPictureUrl).error(R.drawable.missing)
            .into(holder.itemView.beer_picture_item)
    }

    override fun getItemCount(): Int {
        return beers.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(beers: List<Beer>?) {
        this.beers.clear()
        this.beers.addAll(beers ?: emptyList())
        notifyDataSetChanged()
    }

    inner class BeersViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_beer, parent, false)
    ) {
        private val binding = ItemBeerBinding.bind(itemView)

        fun onBind(
            beer: Beer
        ) {
            binding.beerNameItem.text = beer.Name
            binding.beerAlcoolItem.text = beer.Alcool.toString()
            binding.beerEbcItem.text = beer.EBC.toString()
            binding.beerIbuItem.text = beer.IBU.toString()
            binding.beerTypeItem.text = beer.Type
            binding.beerBreweriesItem.text = beer.Breweries.toString()
            binding.imageUrl = beer.Picture
//            binding.beerPictureItem.setImageBitmap(pictureUrls[0]) = beer.Picture.va
        }
    }
}