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
import com.example.lemenestrel.databinding.ItemBeerBinding

class BeersAdapter(val clickListener: BeersListener/*private val onClick: (Beers) -> Unit*/) :
    ListAdapter<Beers, BeersAdapter.ViewHolder>(BeerDiffCallback) {

//    /* ViewHolder for Beer, takes in the inflated view and the onClick behavior. */
//    class BeersViewHolder(itemView: View, val onClick: (Beers) -> Unit) :
//        RecyclerView.ViewHolder(itemView) {
//        private val beerTextView: TextView = itemView.findViewById(R.id.beer_name)
//        //        private val flowerImageView: ImageView = itemView.findViewById(R.id.flower_image)
//        private var currentBeer: Beers? = null
//
//        init {
//            itemView.setOnClickListener {
//                currentBeer?.let {
//                    onClick(it)
//                }
//            }
//        }
//
//        /* Bind flower name and image. */
//        fun bind(beer: Beers) {
//            currentBeer = beer
//
//            beerTextView.text = beer.Name
////            if (beer.image != null) {
////                flowerImageView.setImageResource(beer.image)
////            } else {
////                flowerImageView.setImageResource(R.drawable.rose)
////            }
//        }
//    }
//
//    /* Creates and inflates view and return BeersViewHolder. */
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeersViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_beer, parent, false)
//        return BeersViewHolder(view, onClick)
//    }
//
//    /* Gets current flower and uses it to bind view. */
//    override fun onBindViewHolder(holder: BeersViewHolder, position: Int) {
//        val flower = getItem(position)
//        holder.bind(flower)
//
//    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemBeerBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Beers, clickListener: BeersListener) {
            binding.beerNameItem = item.Name
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

object BeerDiffCallback : DiffUtil.ItemCallback<Beers>() {
    override fun areItemsTheSame(oldItem: Beers, newItem: Beers): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Beers, newItem: Beers): Boolean {
        return oldItem.Name == newItem.Name
    }
}

class BeersListener(val clickListener: (beerName: String) -> Unit) {
    fun onClick(beer: Beers) = clickListener(beer.Name)
}
