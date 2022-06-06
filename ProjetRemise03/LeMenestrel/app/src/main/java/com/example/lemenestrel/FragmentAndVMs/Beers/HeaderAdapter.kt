package com.example.lemenestrel.FragmentAndVMs.Beers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lemenestrel.R

// A list always displaying one element: the number of beers
class HeaderAdapter: RecyclerView.Adapter<HeaderAdapter.BeerViewHolder>() {
    private var beersCount: Int = 0

    /* ViewHolder for displaying header. */
    class BeerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val promotedBeersNumber: TextView = itemView.findViewById(R.id.promoted_beer_number)

        fun bind(beersCount: Int) {
            promotedBeersNumber.text = beersCount.toString()
        }
    }

    /* Inflates view and returns BeersViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_beers, parent, false)
        return BeerViewHolder(view)
    }

    /* Binds number of beers to the header. */
    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        holder.bind(beersCount)
    }

    /* Returns number of items, since there is only one item in the header return one  */
    override fun getItemCount(): Int {
        return 1
    }

    /* Updates header to display number of beers when a beer is added or subtracted. */
    fun updateBeersCount(updatedBeerCount: Int) {
        beersCount = updatedBeerCount
        notifyDataSetChanged()
    }
}