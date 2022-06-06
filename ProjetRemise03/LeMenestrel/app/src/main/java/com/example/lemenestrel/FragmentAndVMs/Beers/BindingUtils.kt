package com.example.lemenestrel.FragmentAndVMs.Beers

import android.graphics.Picture
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.lemenestrel.Database.Models.Beers
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage


//import com.example.android.trackmysleepquality.R
//import com.example.android.trackmysleepquality.convertDurationToFormatted
//import com.example.android.trackmysleepquality.convertNumericQualityToString
//import com.example.android.trackmysleepquality.database.Beers


//@BindingAdapter("sleepQualityString")
//fun TextView.setSleepQualityString(item: Beers?) {
//    item?.let {
//        text = convertNumericQualityToString(item.sleepQuality, context.resources)
//    }
//}
//
//

//private fun getBeerWithName(name: String): Beers {
//    val beers: MutableList<Beers> = mutableListOf()
//    val ref = FirebaseDatabase.getInstance().getReference("Beers")
//    var beer: Beers("", "", 0, mutableListOf(), 0, 0, "")
//    ref.addListenerForSingleValueEvent(object: ValueEventListener {
//        override fun onDataChange(dataSnapshot: DataSnapshot) {
//            if (dataSnapshot.exists()) {
//                val children = dataSnapshot!!.children
//                children.forEach {
//                    // This returns every brewery in a single String
//                    val breweriesString: Array<String> = arrayOf(it.child("breweries").value.toString())
//                    val beerName = it.child("name").value.toString()
//                    val beerType = it.child("type").value.toString()
//                    val beerAlcool = Integer.parseInt(it.child("alcool").value.toString())
//                    val beerEbc = Integer.parseInt(it.child("ebc").value.toString())
//                    val beerIbu = Integer.parseInt(it.child("ibu").value.toString())
//                    val beerPicture = it.child("picture").value.toString()
//
//                    if (beerName == name)
//                        beer = Beers(beerName, beerType, beerAlcool, breweriesString.toMutableList(), beerEbc, beerIbu, beerPicture)
//
////                    beers.add(beer)
//                }
//            }
//        }
//        override fun onCancelled(error: DatabaseError) {
//            throw error.toException()
//        }
//    })
//    return beer
//}

//@BindingAdapter("beerPicture")
//fun ImageView.setSleepImage(item: Beers?) {
//    val storageReference = FirebaseStorage.getInstance().reference
//    val photoReference = storageReference.child("PictureFolder/beer_")
//    item?.let {
//        setImageResource(getBeerWithName(item.Name).Picture))
////        {
////            0 -> R.drawable.admin
////            1 -> R.drawable.ic_sleep_1
////            2 -> R.drawable.ic_sleep_2
////
////            3 -> R.drawable.ic_sleep_3
////
////            4 -> R.drawable.ic_sleep_4
////            5 -> R.drawable.ic_sleep_5
////            else -> R.drawable.ic_sleep_active
////        })
//    }
//}