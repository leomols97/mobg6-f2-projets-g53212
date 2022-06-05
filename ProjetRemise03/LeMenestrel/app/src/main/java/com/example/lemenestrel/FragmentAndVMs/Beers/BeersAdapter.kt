package com.example.lemenestrel.FragmentAndVMs.Beers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lemenestrel.Database.Models.Beers
import com.example.lemenestrel.R

class BeersAdapter(private val onClick: (Beers) -> Unit) :
    ListAdapter<Beers, BeersAdapter.BeersViewHolder>(FlowerDiffCallback) {

    /* ViewHolder for Flower, takes in the inflated view and the onClick behavior. */
    class BeersViewHolder(itemView: View, val onClick: (Beers) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val beerTextView: TextView = itemView.findViewById(R.id.picture_name)
        //        private val flowerImageView: ImageView = itemView.findViewById(R.id.flower_image)
        private var currentBeer: Beers? = null

        init {
            itemView.setOnClickListener {
                currentBeer?.let {
                    onClick(it)
                }
            }
        }

        /* Bind flower name and image. */
        fun bind(beer: Beers) {
            currentBeer = beer

            beerTextView.text = beer.BeerName
//            if (beer.image != null) {
//                flowerImageView.setImageResource(beer.image)
//            } else {
//                flowerImageView.setImageResource(R.drawable.rose)
//            }
        }
    }

    /* Creates and inflates view and return BeersViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_beer, parent, false)
        return BeersViewHolder(view, onClick)
    }

    /* Gets current flower and uses it to bind view. */
    override fun onBindViewHolder(holder: BeersViewHolder, position: Int) {
        val flower = getItem(position)
        holder.bind(flower)

    }
}

object FlowerDiffCallback : DiffUtil.ItemCallback<Beers>() {
    override fun areItemsTheSame(oldItem: Beers, newItem: Beers): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Beers, newItem: Beers): Boolean {
        return oldItem.BeerName == newItem.BeerName
    }
}