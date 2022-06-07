package com.example.lemenestrel.FragmentAndVMs.Beers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lemenestrel.Database.Models.Beers
import com.example.lemenestrel.R
import kotlinx.android.synthetic.main.item_beer.view.*
import kotlinx.android.synthetic.main.item_beer.*;

class BeersAdapter(val urls: List<String>, val beers: List<Beers>): RecyclerView.Adapter<BeersAdapter.BeersViewHolder>() {

//    inner class BeersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeersViewHolder {
//         return BeersViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.item_beer, parent, false) )
//    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeersViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.item_beer, parent, false)
//        return BeersViewHolder(view)
//    }

//    override fun onBindViewHolder(holder: BeersViewHolder, position: Int) = holder.bind(items[position])

//    override fun onBindViewHolder(holder: BeersViewHolder, position: Int) { // = holder.bind(urls[position]) {
////        val itemBeerBinding: RecyclerView.ViewHolder = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.fragment_admin_beer, container, false)
//        val url = urls[position]
//        val beerName = beers[position].Name
//        val beerAlcool = beers[position].Alcool
//        val beerEbc = beers[position].EBC
//        val beerIbu = beers[position].IBU
//        val beerBreweries = beers[position].Breweries
//        val beerType = beers[position].Type
//        Glide.with(holder.itemView.context).load(url).error(R.drawable.missing).into(holder.itemView.beer_picture_item)

//        val textView: TextView = R.id.beer_name_item as TextView
//        textView.setOnClickListener {
//            textView.text = beerName
//        }
//        Glide.with(holder.itemView.context).load(beerName).into(R.layout.item_beer)
//      }

//    override fun getItemCount(): Int {
//        return urls.size
//    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeersViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = ItemBeerBinding.inflate(inflater)
//        return BeersViewHolder(binding)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeersViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_beer, parent,false)
        return BeersViewHolder(itemView)

    }

    override fun getItemCount(): Int = beers.size

//    override fun onBindViewHolder(holder: BeersViewHolder, position: Int) { // = holder.bind(beers[position]) // {
//        val url = urls[position]
////        Glide.with(holder.itemView.context).load(url).error(R.drawable.missing).into(holder.itemView.beer_picture_item)
//        Glide.with(holder.itemView.context /* context */)
//            .load(url)
//            .into(holder.itemView.beer_picture_item)
//    }

    override fun onBindViewHolder(holder: BeersViewHolder, position: Int) {

        val currentitem = beers[position]
        val url = urls[position]

        holder.beerName.text = currentitem.Name
        holder.beerAlcool.text = currentitem.Alcool.toString()
        holder.beerBreweries.text = currentitem.Breweries.toString()
        holder.beerEbc.text = currentitem.EBC.toString()
        holder.beerIbu.text = currentitem.IBU.toString()
        holder.beerType.text = currentitem.Type
        Glide.with(holder.itemView.context).load(url).error(R.drawable.missing).into(holder.itemView.beer_picture_item)
    }

//    inner class BeersViewHolder(val binding: ItemBeerBinding) : RecyclerView.ViewHolder(binding.root) {
////        val url = urls[position]
//        fun bind(item: Beers) {
//            with(binding) {
//                beerNameItem.text = item.Name
//                beerAlcoolItem.text = item.Alcool.toString()
//                beerBreweriesItem.text = item.Breweries.toString()
//                beerEbcItem.text = item.EBC.toString()
//                beerIbuItem.text = item.IBU.toString()
//                beerTypeItem.text = item.Type
//            }
//        }
////        Glide.with(holder.itemView.context).load(beerName).into(R.layout.item_beer)
//    }

    class BeersViewHolder(itemBeer : View) : RecyclerView.ViewHolder(itemBeer){

        val beerName : TextView = itemBeer.findViewById(R.id.beer_name_item)
        val beerType : TextView = itemBeer.findViewById(R.id.beer_type_item)
        val beerAlcool : TextView = itemBeer.findViewById(R.id.beer_alcool_item)
        val beerBreweries : TextView = itemBeer.findViewById(R.id.beer_breweries_item)
        val beerEbc : TextView = itemBeer.findViewById(R.id.beer_ebc_item)
        val beerIbu : TextView = itemBeer.findViewById(R.id.beer_ibu_item)
        val beerPicture : ImageView = itemBeer.findViewById(R.id.beer_picture_item)
    }
}